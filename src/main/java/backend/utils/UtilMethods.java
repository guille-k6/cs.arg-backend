package backend.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class UtilMethods {
    public static int tryParsePageNumber(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }


    // Returns a pageable based in the parameters you input. Page and maxItemsPerPage are mandatory non null.
    public static Pageable getPageable(int page, int maxItemsPerPage, String sortAttribute, String direction){
        Pageable pageable;
        if(sortAttribute == null){
            pageable = PageRequest.of(page, maxItemsPerPage);
        }else if(direction == null){
            // Sorted default descending
            pageable = PageRequest.of(page, maxItemsPerPage, Sort.by(sortAttribute).ascending());
        }else if(direction.equalsIgnoreCase("ASC")){
            pageable = PageRequest.of(page, maxItemsPerPage, Sort.by(sortAttribute).ascending());
        }else if(direction.equalsIgnoreCase("DESC")){
            pageable = PageRequest.of(page, maxItemsPerPage, Sort.by(sortAttribute).descending());
        }else{
            // Just in case
            pageable = PageRequest.of(0, 15);
        }
        return pageable;
    }
}
