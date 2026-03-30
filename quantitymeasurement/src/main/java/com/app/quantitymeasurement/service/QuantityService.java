package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.dto.QuantityDTO;
import com.app.quantitymeasurement.model.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class QuantityService implements IQuantityMeasurementService {


    private static final Logger log = LoggerFactory.getLogger(QuantityService.class);

    private final QuantityMeasurementRepository repository;

    public QuantityService(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    // OPERATIONS

    @Override
    public QuantityMeasurementDTO add(QuantityDTO dto) {

        log.info("ADD operation started with values: {} and {}", dto.getValue1(), dto.getValue2());

        double result = dto.getValue1() + dto.getValue2();

        log.debug("ADD result calculated: {}", result);

        return performOperation(dto, result);
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO dto) {

        log.info("SUBTRACT operation started");

        double result = dto.getValue1() - dto.getValue2();

        log.debug("SUBTRACT result: {}", result);

        return performOperation(dto, result);
    }

    @Override
    public QuantityMeasurementDTO multiply(QuantityDTO dto) {

        log.info("MULTIPLY operation started");

        double result = dto.getValue1() * dto.getValue2();

        log.debug("MULTIPLY result: {}", result);

        return performOperation(dto, result);
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO dto) {

        log.info("DIVIDE operation started");

        if (dto.getValue2() == 0) {
            log.error("Divide by zero error for input: {}", dto);
            return errorResponse(dto, "Cannot divide by zero");
        }

        double result = dto.getValue1() / dto.getValue2();

        log.debug("DIVIDE result: {}", result);

        return performOperation(dto, result);
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO dto) {

        log.info("COMPARE operation between {} {} and {} {}",
                dto.getValue1(), dto.getUnit1(),
                dto.getValue2(), dto.getUnit2());

        double v1 = dto.getValue1();
        double v2 = dto.getValue2();

        String message;

        if (v1 > v2) {
            message = v1 + " " + dto.getUnit1() + " is greater than " + v2 + " " + dto.getUnit2();
        } else if (v1 < v2) {
            message = v1 + " " + dto.getUnit1() + " is less than " + + v2 + " " + dto.getUnit2();
        } else {
            message = "Both are equal";
        }

        log.debug("COMPARE result: {}", message);

        QuantityMeasurementEntity entity = buildEntity(dto);
        entity.setResult(0.0);
        entity.setError(false);
        entity.setErrorMessage(message);

        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO dto) {

        log.info("CONVERT operation started");

        double result = dto.getValue1() * 100; // simple logic

        log.debug("CONVERT result: {}", result);

        return performOperation(dto, result);
    }

    

    private QuantityMeasurementDTO performOperation(QuantityDTO dto, double result) {

        log.info("Saving successful operation to database");

        QuantityMeasurementEntity entity = buildEntity(dto);

        entity.setResult(result);
        entity.setError(false);

        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }

    private QuantityMeasurementDTO errorResponse(QuantityDTO dto, String message) {

        log.warn("Saving error response: {}", message);

        QuantityMeasurementEntity entity = buildEntity(dto);

        entity.setError(true);
        entity.setErrorMessage(message);

        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }

    private QuantityMeasurementEntity buildEntity(QuantityDTO dto) {

        log.debug("Building entity from DTO");

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperation(dto.getOperation());
        entity.setMeasurementType(dto.getMeasurementType());
        entity.setValue1(dto.getValue1());
        entity.setValue2(dto.getValue2());
        entity.setUnit1(dto.getUnit1());
        entity.setUnit2(dto.getUnit2());

        return entity;
    }

    // HISTORY 

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {

        log.info("Fetching history for operation: {}", operation);

        return QuantityMeasurementDTO.fromEntityList(repository.findByOperation(operation));
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {

        log.info("Fetching measurements by type: {}", type);

        return QuantityMeasurementDTO.fromEntityList(repository.findByMeasurementType(type));
    }

    @Override
    public long getOperationCount(String operation) {

        log.info("Counting operations for: {}", operation);

        return repository.countByOperation(operation);
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {

        log.info("Fetching error history");

        return QuantityMeasurementDTO.fromEntityList(repository.findByIsErrorTrue());
    }
}