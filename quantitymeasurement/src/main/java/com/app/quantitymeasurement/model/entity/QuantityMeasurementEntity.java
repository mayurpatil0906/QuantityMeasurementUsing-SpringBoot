package com.app.quantitymeasurement.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements")
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

   
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}