package de.enmacc.task.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ContractAlreadyExistsException extends RuntimeException {

    private String companyA;
    private String companyB;

}
