package de.enmacc.task.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"company_a", "company_b"})
})
public class ContractEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "company_a", nullable = false)
    private String companyA;

    @Column(name = "company_b", nullable = false)
    private String companyB;

}
