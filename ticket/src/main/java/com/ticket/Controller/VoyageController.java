package com.ticket.Controller;


import com.ticket.Dto.VoyageCreateRequestDto;
import com.ticket.Model.User;
import com.ticket.Model.Voyage;
import com.ticket.Service.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/voyages")
public class VoyageController {

    @Autowired
    private VoyageService voyageService;

    @PostMapping("/create")
    public Voyage createVoyage(@RequestBody VoyageCreateRequestDto voyageCreateRequestDto) {
        return voyageService.createVoyage(voyageCreateRequestDto);
    }

    @GetMapping("/{voyageId}")
    public Voyage getByVoyageId(@PathVariable Integer voyageId) {

        return voyageService.getByVoyageId(voyageId);
    }
    @PutMapping("/{voyageId}")
    public Voyage updateVoyage(@PathVariable Integer voyageId, @RequestBody VoyageCreateRequestDto voyageCreateRequestDto) {
        return voyageService.updateVoyage(voyageId, voyageCreateRequestDto);
    }
    @DeleteMapping("/canceled/{voyageId}")
    public Voyage deleteVoyage(@PathVariable Integer voyageId,@RequestBody User userRequest) {
        return voyageService.deleteVoyage(voyageId,userRequest);
    }

    @GetMapping("/search")
    public List<Voyage> getByVoyageSearch(@RequestBody VoyageCreateRequestDto voyageCreateRequestDto) {
        return voyageService.getByVoyageSearch(voyageCreateRequestDto);
    }
}
