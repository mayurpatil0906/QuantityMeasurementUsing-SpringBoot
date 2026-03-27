package com.app.quantitymeasurement.model.dto;

public class QuantityDTO {

    private Double value1;
    private Double value2;
    private String unit1;
    private String unit2;
    private String operation;
    private String measurementType;



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

    public String getOperation() {
        return operation;
    }

    public String getMeasurementType() {
        return measurementType;
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

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }
}