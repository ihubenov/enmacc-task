package de.enmacc.task.unit.service;

import de.enmacc.task.dto.ContractDto;
import de.enmacc.task.entity.ContractEntity;
import de.enmacc.task.exception.ContractAlreadyExistsException;
import de.enmacc.task.mapper.ContractMapper;
import de.enmacc.task.repository.ContractRepository;
import de.enmacc.task.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {

    @Mock
    private ContractRepository contractRepository;
    @Mock
    private ContractMapper contractMapper;

    private ContractService contractService;

    @BeforeEach
    public void init() {
        this.contractService = new ContractService(contractRepository, contractMapper);
    }

    @Test
    public void testAddContract() {
        ContractEntity entity = ContractEntity.builder()
                .companyA("A")
                .companyB("B")
                .build();
        ContractEntity persistedEntity = generateEntity("A", "B");
        ContractDto convertedDto = ContractDto.builder()
                .companyA("A")
                .companyB("B")
                .build();

        when(contractRepository.save(entity)).thenReturn(persistedEntity);
        when(contractMapper.entityToDto(persistedEntity)).thenReturn(convertedDto);

        ContractDto dto = contractService.addContract("A", "B");
        assertEquals("A", dto.getCompanyA());
        assertEquals("B", dto.getCompanyB());

    }

    @Test
    public void testAddContractConstraintViolation() {
        ContractEntity entity = ContractEntity.builder()
                .companyA("A")
                .companyB("B")
                .build();
        when(contractRepository.save(entity)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(ContractAlreadyExistsException.class, () -> contractService.addContract("A", "B"));
    }

    @Test
    public void testGetAllPossibleSleeves() {
        when(contractRepository.findAll()).thenReturn(generateEntities());

        List<String> result = contractService.getAllPossibleSleeves("A", "B");

        assertEquals(3, result.size());
        assertTrue(result.containsAll(List.of("A->B", "A->C->B", "A->E->B")));
    }

    private List<ContractEntity> generateEntities() {
        return List.of(
                generateEntity("A", "B"),
                generateEntity("A","C"),
                generateEntity("A","D"),
                generateEntity("A","E"),
                generateEntity("B","C"),
                generateEntity("B","E")
        );
    }

    private ContractEntity generateEntity(String companyA, String companyB) {
        return ContractEntity.builder()
                .id(UUID.randomUUID())
                .companyA(companyA)
                .companyB(companyB)
                .build();
    }
}
