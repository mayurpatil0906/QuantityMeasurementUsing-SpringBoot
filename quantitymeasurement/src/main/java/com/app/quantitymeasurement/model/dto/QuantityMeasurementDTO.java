package com.app.quantitymeasurement.model.dto;

import com.app.quantitymeasurement.model.entity.QuantityMeasurementEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementDTO {

    private Long id;
    private String operation;
    private String measurementType;

    private Double value1;
    private Double value2;

    private String unit1;
    private String unit2;

    private Double result;

    private boolean isError;
    private String errorMessage;

    private LocalDateTime createdAt;

    //  GETTERS 

    public Long getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public Double getValue1() {
        return value1;
    }

    public Double getValue2() {
        return value2;
    }

    public String getUnit1() {
        return unit1;
    }

    public String getUnit2() {
        return unit2;
    }

    public Double getResult() {
        return result;
    }

    public boolean isError() {
        return isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // SETTERS 

    public void setId(Long id) {
        this.id = id;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public void setValue1(Double value1) {
        this.value1 = value1;
    }

    public void setValue2(Double value2) {
        this.value2 = value2;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    //CONVERSION METHODS 

    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();

        dto.setId(entity.getId());
        dto.setOperation(entity.getOperation());
        dto.setMeasurementType(entity.getMeasurementType());
        dto.setValue1(entity.getValue1());
        dto.setValue2(entity.getValue2());
        dto.setUnit1(entity.getUnit1());
        dto.setUnit2(entity.getUnit2());
        dto.setResult(entity.getResult());
        dto.setError(entity.isError());
        dto.setErrorMessage(entity.getErrorMessage());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public QuantityMeasurementEntity toEntity() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperation(this.operation);
        entity.setMeasurementType(this.measurementType);
        entity.setValue1(this.value1);
        entity.setValue2(this.value2);
        entity.setUnit1(this.unit1);
        entity.setUnit2(this.unit2);
        entity.setResult(this.result);
        entity.setError(this.isError);
        entity.setErrorMessage(this.errorMessage);

        return entity;
    }

    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .collect(Collectors.toList());
    }
}