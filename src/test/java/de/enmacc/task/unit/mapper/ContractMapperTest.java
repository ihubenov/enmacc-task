package de.enmacc.task.unit.mapper;

import de.enmacc.task.dto.ContractDto;
import de.enmacc.task.entity.ContractEntity;
import de.enmacc.task.mapper.ContractMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ContractMapperTest {

    private ContractMapper contractMapper;


    @BeforeEach
    public void init() {
        contractMapper = Mappers.getMapper(ContractMapper.class);
    }

    @Test
    public void testEntityToDto() {
        ContractEntity entity = ContractEntity.builder()
                .id(UUID.randomUUID())
                .companyA("A")
                .companyB("B")
                .build();

        ContractDto dto = contractMapper.entityToDto(entity);
        assertEquals("A", dto.getCompanyA());
        assertEquals("B", dto.getCompanyB());
    }

    @Test
    public void testDtoToEntity() {
        ContractDto dto = ContractDto.builder()
                .companyA("A")
                .companyB("B")
                .build();

        ContractEntity entity = contractMapper.dtoToEntity(dto);
        assertNull(entity.getId());
        assertEquals("A", dto.getCompanyA());
        assertEquals("B", dto.getCompanyB());
    }

}
