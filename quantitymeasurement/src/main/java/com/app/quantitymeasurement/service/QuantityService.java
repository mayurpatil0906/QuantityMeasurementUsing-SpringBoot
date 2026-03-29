package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.dto.QuantityDTO;
import com.app.quantitymeasurement.model.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityService implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityService(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO dto) {
        return performOperation(dto, dto.getValue1() + dto.getValue2());
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO dto) {
        return performOperation(dto, dto.getValue1() - dto.getValue2());
    }

    @Override
    public QuantityMeasurementDTO multiply(QuantityDTO dto) {
        return performOperation(dto, dto.getValue1() * dto.getValue2());
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO dto) {
        if (dto.getValue2() == 0) {
            return errorResponse(dto, "Cannot divide by zero");
        }
        return performOperation(dto, dto.getValue1() / dto.getValue2());
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO dto) {

        double v1 = dto.getValue1();
        double v2 = dto.getValue2();

        String message;

        if (v1 > v2) {
            message = v1 + " " + dto.getUnit1() + " is greater than " + v2 + " " + dto.getUnit2();
        } else if (v1 < v2) {
            message = v1 + " " + dto.getUnit1() + " is less than " + v2 + " " + dto.getUnit2();
        } else {
            message = "Both are equal";
        }

        QuantityMeasurementEntity entity = buildEntity(dto);
        entity.setResult(0.0);
        entity.setError(false);
        entity.setErrorMessage(message);

        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO dto) {
        double result = dto.getValue1() * 100; // simple logic
        return performOperation(dto, result);
    }

    // ================= COMMON METHODS =================

    private QuantityMeasurementDTO performOperation(QuantityDTO dto, double result) {
        QuantityMeasurementEntity entity = buildEntity(dto);

        entity.setResult(result);
        entity.setError(false);

        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }

    private QuantityMeasurementDTO errorResponse(QuantityDTO dto, String message) {
        QuantityMeasurementEntity entity = buildEntity(dto);

        entity.setError(true);
        entity.setErrorMessage(message);

        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }

    private QuantityMeasurementEntity buildEntity(QuantityDTO dto) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperation(dto.getOperation());
        entity.setMeasurementType(dto.getMeasurementType());
        entity.setValue1(dto.getValue1());
        entity.setValue2(dto.getValue2());
        entity.setUnit1(dto.getUnit1());
        entity.setUnit2(dto.getUnit2());

        return entity;
    }

    //HISTORY 

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByOperation(operation));
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByMeasurementType(type));
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperation(operation);
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromEntityList(repository.findByIsErrorTrue());
    }
}