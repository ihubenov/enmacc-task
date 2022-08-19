package de.enmacc.task.unit.controller;

import de.enmacc.task.controller.ContractController;
import de.enmacc.task.dto.ContractDto;
import de.enmacc.task.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
public class ContractControllerTest {

    @Mock
    private ContractService contractService;

    private ContractController contractController;

    @BeforeEach
    public void init() {
        contractController = new ContractController(contractService);
    }

    @Test
    public void testAddContract() {
        ContractDto contractDto = new ContractDto("A", "B");

        when(contractService.addContract("A", "B"))
                .thenReturn(contractDto);

        ResponseEntity<ContractDto> response = contractController.addContract(contractDto);
        assertEquals(CREATED, response.getStatusCode());
        assertEquals("A", response.getBody().getCompanyA());
        assertEquals("B", response.getBody().getCompanyB());
    }

    @Test
    public void testGetAllPossibleSleeves() {
        ContractDto contractDto = new ContractDto("A", "B");

        when(contractService.getAllPossibleSleeves("A", "B"))
                .thenReturn(List.of("A->B", "A->C->B", "A->E->B"));

        ResponseEntity<List<String>> response = contractController.getAllPossibleSleeves("A", "B");
        assertEquals(OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
        assertTrue(response.getBody().containsAll(List.of("A->B", "A->C->B", "A->E->B")));
    }

}
