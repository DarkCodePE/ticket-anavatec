package com.peterson.helpdesk.resources;

import com.peterson.helpdesk.domain.Chamado;
import com.peterson.helpdesk.domain.dtos.ProductListDTO;
import com.peterson.helpdesk.domain.dtos.SolutionListDTO;
import com.peterson.helpdesk.domain.dtos.SolutionRequestDTO;
import com.peterson.helpdesk.domain.dtos.SolutionResponseDTO;
import com.peterson.helpdesk.services.SolutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j(topic = "SOLUTION_CONTROLLER")
@RequestMapping(value = "/solution")
public class SolutionController {
    private final SolutionService solutionService;

    public SolutionController(SolutionService solutionService) {
        this.solutionService = solutionService;
    }
    @GetMapping
    public ResponseEntity<List<SolutionResponseDTO>> getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(solutionService.solutions());
    }
    @GetMapping("/product")
    public ResponseEntity<List<SolutionResponseDTO>> getSolutionsByProductSku(@Valid @RequestParam Integer productID){
        return ResponseEntity.status(HttpStatus.OK)
                .body(solutionService.getSolutionsByProductId(productID));
    }
    @GetMapping("/ticket")
    public ResponseEntity<List<Chamado>> getTicketsByProductSku(@Valid @RequestParam Integer productID){
        return ResponseEntity.status(HttpStatus.OK)
                .body(solutionService.getTicketsByProductSku(productID));
    }
    @GetMapping("/tickets")
    public ResponseEntity<List<SolutionResponseDTO>> getSolutionsByTicketId(@Valid @RequestParam Integer ticketID){
        List<SolutionResponseDTO> solutionResponseDTO = solutionService.getSolutionsByTicketId(ticketID);
        if (solutionResponseDTO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(solutionResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(solutionResponseDTO);
    }
    @PostMapping("/create")
    public ResponseEntity<List<SolutionListDTO>> createSolution(@RequestPart SolutionRequestDTO solutionRequestDTO, @RequestPart MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(solutionService.createSolution(solutionRequestDTO, file));
    }
}
