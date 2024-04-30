package backend.controllers;

import backend.models.Crate;
import backend.service.CrateService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/crates")
public class CrateController {
    private final CrateService crateService;

    @Autowired
    public CrateController(CrateService crateService){
        this.crateService = crateService;
    }

    @GetMapping()
    public ResponseEntity<?> getCrates(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "page", required = false) String page,
                                         @RequestParam(value = "sort", required = false) String sortAttribute,
                                         @RequestParam(value = "direction", required = false) String direction) {
        HashMap<String, String> parameters = new HashMap<>();
        if(!StringUtils.isBlank(name)) parameters.put("name", name);
        if(!StringUtils.isBlank(page)) parameters.put("page", (page)); // page is in string
        if(!StringUtils.isBlank(sortAttribute)) parameters.put("sortAttribute", sortAttribute);
        if(!StringUtils.isBlank(direction)) parameters.put("direction", direction);
        try {
            Page<Crate> crates = this.crateService.findCratesFiltered(parameters);
            return ResponseEntity.ok(crates);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
