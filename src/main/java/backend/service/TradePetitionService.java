package backend.service;

import backend.config.TradePetitionParser;
import backend.models.TradePetition;
import backend.models.dtoResponse.DtoTradePetition_o;
import backend.models.*;
import backend.models.dtoResponse.*;
import backend.repository.TradePetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static backend.utils.UtilMethods.getPageable;
import static backend.utils.UtilMethods.tryParsePageNumber;

@Service
public class TradePetitionService {

    private final TradePetitionParser tradePetitionParser;
    private final TradePetitionRepository tradePetitionRepository;
    private final int MAX_TRADE_PETITIONS_PER_PAGE = 15;

    @Autowired
    public TradePetitionService(TradePetitionRepository tradePetitionRepository, TradePetitionParser tradePetitionParser){
        this.tradePetitionRepository = tradePetitionRepository;
        this.tradePetitionParser = tradePetitionParser;
    }

    /**
     * Gets a max of 15 trade petitions
     * @param page number of the page of the trade petitions
     * @return page of 15 trade petitions
     */
    public Page<DtoTradePetition_o> getAllUnfiltered(String page) throws Exception {
        int pageNumber = tryParsePageNumber(page, 0);
        Pageable pageable = PageRequest.of(pageNumber, MAX_TRADE_PETITIONS_PER_PAGE, Sort.by("creation_ms").descending());
        Page<TradePetition> tradePetitions = tradePetitionRepository.getAllTradePetitions(pageable);
        // Parse the pure trade petitions to the DtoTradePetition
        return tradePetitions.map(tradePetitionParser::tradePetitionToDTO);
    }

    /**
     *  Takes a map of parameters and returns a page of TradePetitions
     * @param parameters map of parameters
     * @return page of the data transfer object TradePetition
     * @throws Exception when the parameters of the Tradepeetition are wrong
     */
    public Page<DtoTradePetition_o> getTradePetitionsFiltered(HashMap<String, String> parameters) throws Exception {
        String search_type = null;
        Boolean parsed_search_type = null;
        String item_type = null;
        String name = null;
        String weapon = null;
        String rarity = null;
        String condition = null;
        int pattern = -1;
        boolean stattrak = false;
        boolean souvenir = false;
        boolean special = false;
        int page = 0;
        String sortAttribute = null;
        String direction = null;

        final Pageable pageable;
        // TradePetitions page that will be returned
        Page<DtoTradePetition_o> tradePetitionsDto;

        if (parameters.size() == 0) {
            pageable = PageRequest.of(0, MAX_TRADE_PETITIONS_PER_PAGE);
            return tradePetitionRepository.getAllTradePetitions(pageable).map(tradePetitionParser::tradePetitionToDTO);
        }

        // If the parameter exists -> Underscores to spaces -> Assign to the variable.
        if (parameters.containsKey("search_type")) {
            search_type = parameters.get("search_type").replaceAll("_", " ").toLowerCase();
            if (search_type.equalsIgnoreCase("petition")) {
                parsed_search_type = false;
            } else if (search_type.equalsIgnoreCase("offer")) {
                parsed_search_type = true;
            }
        }
        if (parameters.containsKey("item_type")){
            item_type = parameters.get("item_type").replaceAll("_", " ").toLowerCase();
        }
        if (parameters.containsKey("name")){
            name = parameters.get("name").replaceAll("_", " ").toLowerCase();
        }
        if (parameters.containsKey("weapon")){
            weapon = parameters.get("weapon").replaceAll("_", " ");         // I don't need it lowered because
        }
        if (parameters.containsKey("rarity")){
            rarity = parameters.get("rarity").replaceAll("_", " ");         // these parameters came from a select in
        }
        if (parameters.containsKey("condition")){
            condition = parameters.get("condition").replaceAll("_", " ");// the frontend.
        }
        if (parameters.containsKey("pattern")){
            pattern = Integer.parseInt(parameters.get("pattern"));
        }
        if (parameters.containsKey("stattrak")){
            stattrak = Boolean.parseBoolean(parameters.get("stattrak"));
        }
        if (parameters.containsKey("souvenir")){
            souvenir = Boolean.parseBoolean(parameters.get("souvenir"));
        }
        if (parameters.containsKey("special")){
            special = Boolean.parseBoolean(parameters.get("special"));
        }
        if (parameters.containsKey("page")){
            page = tryParsePageNumber(parameters.get("page"), 0);
        }
        if (parameters.containsKey("sortAttribute")){
            sortAttribute = parameters.get("sortAttribute").replaceAll("_", " ").toLowerCase();
        }
        if (parameters.containsKey("direction")){
            direction = parameters.get("direction").replaceAll("_", " ").toLowerCase();
        }

        pageable = getPageable(page, MAX_TRADE_PETITIONS_PER_PAGE, sortAttribute, direction);

        if (item_type == null) {
            tradePetitionsDto = getAllUnfiltered(String.valueOf(page));
        } else if (item_type.equalsIgnoreCase("SKIN")) {
            tradePetitionsDto = tradePetitionRepository.getTradePetitionsWeapon(parsed_search_type, name, weapon, rarity, condition, pattern, stattrak, souvenir, special, pageable).map(tradePetitionParser::tradePetitionToDTO);
        } else if (item_type.equalsIgnoreCase("CRATE")) {
            tradePetitionsDto = tradePetitionRepository.getTradePetitionsCrate(parsed_search_type, name, pageable).map(tradePetitionParser::tradePetitionToDTO);
        } else if (item_type.equalsIgnoreCase("STICKER")) {
            tradePetitionsDto = tradePetitionRepository.getTradePetitionsSticker(parsed_search_type, name, pageable).map(tradePetitionParser::tradePetitionToDTO);
        } else {
            throw new Exception("No se pudo procesar la petición.");
        }
        return tradePetitionsDto;
    }

    public boolean createTradePetition(TradePetition tradePetition) {
        TradePetition updatedTradePetition;
        try {
            updatedTradePetition = tradePetitionRepository.save(tradePetition);
        } catch (Exception e) {
            return false;
        }
        if (updatedTradePetition.getId() == null || updatedTradePetition.getId() < 0){
            return false;
        }
        return true;
    }
}
