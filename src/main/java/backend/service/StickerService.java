package backend.service;

import backend.models.Skin;
import backend.models.Sticker;
import backend.repository.StickerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static backend.utils.UtilMethods.tryParsePageNumber;

@Service
public class StickerService {

    private final StickerRepository stickerRepository;

    @Autowired
    public StickerService(StickerRepository stickerRepository){
        this.stickerRepository = stickerRepository;
    }

    @Transactional
    public void saveAll(List<Sticker> stickers){
        this.stickerRepository.saveAll(stickers);
    }


    public Page<Sticker> findStickersFiltered(HashMap<String, String> parameters){
        String name = null;
        String rarity = null;
        String page = null;
        String sortAttribute = null;
        String direction = null;
        final Pageable pageable;
        final int MAX_STICKERS_PER_PAGE = 20;

        if (parameters.size() == 0){
            pageable = PageRequest.of(0, MAX_STICKERS_PER_PAGE);
            return this.stickerRepository.findAll(pageable);
        }

        // If the parameter exists -> Underscores to spaces -> Assign to the variable.
        if(parameters.containsKey("name")) name = parameters.get("name").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("rarity")) rarity = parameters.get("rarity").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("page")) page = parameters.get("page");
        if(parameters.containsKey("sortAttribute")) sortAttribute = parameters.get("sortAttribute").replaceAll("_"," ").toLowerCase();
        if(parameters.containsKey("direction")) direction = parameters.get("direction").replaceAll("_"," ").toLowerCase();

        int numberOfPage = tryParsePageNumber(page, 0);

        if(sortAttribute != null && direction != null && direction.equalsIgnoreCase("ASC")){
            pageable = PageRequest.of(numberOfPage, MAX_STICKERS_PER_PAGE, Sort.by(sortAttribute).ascending());
        }else if(sortAttribute != null && direction != null && direction.equalsIgnoreCase("DESC")){
            pageable = PageRequest.of(numberOfPage, MAX_STICKERS_PER_PAGE, Sort.by(sortAttribute).descending());
        }else{
            pageable = PageRequest.of(numberOfPage, MAX_STICKERS_PER_PAGE);
        }

        return this.stickerRepository.getStickersFiltered(name, rarity, pageable);

    }

    public boolean isValidStickerId(String id) {
        Optional<Sticker> sticker = stickerRepository.findById(id);
        return sticker.isPresent();
    }
}
