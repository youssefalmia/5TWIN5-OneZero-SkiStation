package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.dto.PisteDTO;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.services.IPisteServices;

import java.util.List;

@Tag(name = "\uD83C\uDFBF Piste Management")
@RestController
@RequestMapping("/piste")
@RequiredArgsConstructor
public class PisteRestController {

    private final IPisteServices pisteServices;

    @Operation(description = "Add Piste")
    @PostMapping("/add")
    public Piste addPiste(@RequestBody PisteDTO piste){
        return  pisteServices.addPiste(convertToPiste(piste));
    }
    @Operation(description = "Retrieve all Pistes")
    @GetMapping("/all")
    public List<Piste> getAllPistes(){
        return pisteServices.retrieveAllPistes();
    }

    @Operation(description = "Retrieve Piste by Id")
    @GetMapping("/get/{id-piste}")
    public Piste getById(@PathVariable("id-piste") Long numPiste){
        return pisteServices.retrievePiste(numPiste);
    }

    @Operation(description = "Delete Piste by Id")
    @DeleteMapping("/delete/{id-piste}")
    public void deleteById(@PathVariable("id-piste") Long numPiste){
        pisteServices.removePiste(numPiste);
    }


    private Piste convertToPiste(PisteDTO pisteDTO) {
        Piste piste= new Piste();
        piste.setNumPiste(pisteDTO.getNumPiste());
        piste.setNamePiste(pisteDTO.getNamePiste());
        piste.setColor(pisteDTO.getColor());
        piste.setLength(pisteDTO.getLength());
        piste.setSlope(pisteDTO.getSlope());
        piste.setSkiers(pisteDTO.getSkiers());

        return piste;
    }
}
