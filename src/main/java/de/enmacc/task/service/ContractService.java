package de.enmacc.task.service;

import de.enmacc.task.dto.ContractDto;
import de.enmacc.task.entity.ContractEntity;
import de.enmacc.task.exception.ContractAlreadyExistsException;
import de.enmacc.task.mapper.ContractMapper;
import de.enmacc.task.repository.ContractRepository;
import de.enmacc.task.util.GraphUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    public ContractService (ContractRepository contractRepository,
                            ContractMapper contractMapper) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
    }

    public ContractDto addContract(String companyA, String companyB) {
        try {
            ContractEntity entity = contractRepository.save(ContractEntity.builder()
                    .companyA(companyA)
                    .companyB(companyB)
                    .build());
            return contractMapper.entityToDto(entity);
        } catch (DataIntegrityViolationException e) {
            throw new ContractAlreadyExistsException(companyA, companyB);
        }
    }

    public List<String> getAllPossibleSleeves(String companyA, String companyB) {
        Map<String, List<String>> contractsMap = generateContractsMap();
        List<List<String>> allPaths = GraphUtil.getAllPaths(contractsMap, companyA, companyB);
        return allPaths.stream()
                .map(l -> String.join("->", l))
                .toList();
    }

    public List<ContractDto> generateMockData() {
        List<ContractEntity> data = Arrays.asList(
                new ContractEntity(null, "A", "B"),
                new ContractEntity(null, "A", "C"),
                new ContractEntity(null, "A", "D"),
                new ContractEntity(null, "A", "E"),
                new ContractEntity(null, "B", "C"),
                new ContractEntity(null, "B", "E")
        );
        data = contractRepository.saveAll(data);
        return data.stream()
                .map(contractMapper::entityToDto)
                .toList();
    }

    private Map<String, List<String>> generateContractsMap() {
        List<ContractEntity> contracts = contractRepository.findAll();
        Map<String, List<String>> contractsMap = new HashMap<>(contracts.size());

        contracts.forEach(c -> {
            String c1 = c.getCompanyA();
            String c2 = c.getCompanyB();

            contractsMap.putIfAbsent(c1, new LinkedList<>());
            contractsMap.putIfAbsent(c2, new LinkedList<>());
            contractsMap.get(c1).add(c2);
            contractsMap.get(c2).add(c1);
        });

        return contractsMap;
    }

}
