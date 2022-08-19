package de.enmacc.task.controller;

import de.enmacc.task.dto.ContractDto;
import de.enmacc.task.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    /**
     * Persists the given contract in the database.
     *
     * @param contract The contract to persist
     * @return The persisted contract
     */
    @PostMapping(path = "/contracts")
    public ResponseEntity<ContractDto> addContract(
            @RequestBody @Valid ContractDto contract) {
        return new ResponseEntity<>(contractService.addContract(contract.getCompanyA(), contract.getCompanyB()), HttpStatus.CREATED);
    }

    /**
     * Computes based on the contracts in the database all sleeves for the two given companies.
     *
     * @param companyA A company
     * @param companyB Another company
     * @return The list of sleeves. A sleeve is a list of contracts between the companies.
     */
    @GetMapping(path = "/sleeves")
    public ResponseEntity<List<String>> getAllPossibleSleeves(
            @RequestParam @Valid @NotBlank String companyA,
            @RequestParam @Valid @NotBlank String companyB) {
        return ResponseEntity.ok(contractService.getAllPossibleSleeves(companyA, companyB));
    }

    @PostMapping(path = "/generateMockData")
    public ResponseEntity<List<ContractDto>> generateMockData() {
        return ResponseEntity.ok(contractService.generateMockData());
    }

}
