package com.guille.security.auth;

import com.guille.security.config.JwtService;
import com.guille.security.email.EmailSender;
import com.guille.security.models.dtoRequest.AuthenticationRequest;
import com.guille.security.models.dtoResponse.AuthenticationResponse;
import com.guille.security.models.enums.Role;
import com.guille.security.models.User;
import com.guille.security.repository.UserRepository;
import com.guille.security.service.ConfirmationTokenService;
import com.guille.security.service.UserService;
import com.guille.security.utils.EmailValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;
    private final EmailSender emailSender;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // Check email is valid
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) throw new IllegalStateException("Formato de email incorrecto");
        // Check there are no users with that email
        Optional<User> foundUser = repository.findByEmail(request.getEmail());
        if(foundUser.isPresent()) throw new IllegalStateException("El email elegido está en uso");
        // Check there are no users with that nick
        Optional<User> foundUserNickname = repository.findByNickname(request.getNickname());
        if(foundUserNickname.isPresent()) throw new IllegalStateException("El nick elegido no está disponible");

        // Create the user
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .role(Role.USER)
                .enabled(false)
                .locked(false)
                .build();
        repository.save(user);

        // Generate the confirmation token for the account creation
        final int CONFIRMATION_MINUTES = 30;
        String uuidToken = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                uuidToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(CONFIRMATION_MINUTES),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // Generate a jwtoken
        String jwtToken = jwtService.generateToken(user);

        //Send email
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + confirmationToken;
        emailSender.send(request.getEmail(), buildEmail(request.getNickname() , link));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(request.getNickname())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));
        }catch(AuthenticationException e){
            throw new CustomAuthenticationException("Email y/o contraseña incorrecto");
        }

        // So, if I got to this line, means that the authentication passed
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("Email no encontrado."));

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, user.getNickname());
    }

    @Transactional
    public void validateRegisterRequest(String token) throws FileNotFoundException {
        Optional<ConfirmationToken> theToken = confirmationTokenService.getConfirmationToken(token);
        if(theToken.isEmpty()) throw new FileNotFoundException("Token no válido");
        if(theToken.get().getConfirmedAt() != null) throw new IllegalStateException("El email ya fue confirmado");
        LocalDateTime expiredAt = theToken.get().getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())) throw new IllegalStateException("El token esta vencido");

        userService.enableUser(theToken.get().getUser().getEmail()); // Enable the user.
        confirmationTokenService.setConfirmedAt(token);

    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
