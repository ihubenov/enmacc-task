package de.enmacc.task.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDto {

    @NotBlank(message = "Company name cannot be blank")
    private String companyA;

    @NotBlank(message = "Company name cannot be blank")
    private String companyB;
}
