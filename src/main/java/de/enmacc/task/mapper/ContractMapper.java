package de.enmacc.task.mapper;

import de.enmacc.task.dto.ContractDto;
import de.enmacc.task.entity.ContractEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    ContractDto entityToDto(ContractEntity entity);

    ContractEntity dtoToEntity(ContractDto entity);
}
