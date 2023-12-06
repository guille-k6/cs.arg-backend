package com.guille.security.auth;

import com.guille.security.config.JwtService;
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
    private final int CONFIRMATION_MINUTES = 30;

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
}
