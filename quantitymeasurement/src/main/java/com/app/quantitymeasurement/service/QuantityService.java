package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.dto.QuantityDTO;
import com.app.quantitymeasurement.model.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityService {

    private final QuantityMeasurementRepository repository;

    public QuantityService(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    public QuantityMeasurementEntity calculate(QuantityDTO dto) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperation(dto.getOperation());
        entity.setMeasurementType(dto.getMeasurementType());
        entity.setValue1(dto.getValue1());
        entity.setValue2(dto.getValue2());
        entity.setUnit1(dto.getUnit1());
        entity.setUnit2(dto.getUnit2());

        try {
            double v1 = convertToBase(dto.getValue1(), dto.getUnit1(), dto.getMeasurementType());
            double v2 = convertToBase(dto.getValue2(), dto.getUnit2(), dto.getMeasurementType());

            double result = 0;

            switch (dto.getOperation().toUpperCase()) {

                case "ADD":
                    result = v1 + v2;
                    entity.setResult(result);
                    entity.setErrorMessage("Addition result: " + result);
                    break;

                case "SUBTRACT":
                    result = v1 - v2;
                    entity.setResult(result);
                    entity.setErrorMessage("Subtraction result: " + result);
                    break;
                case "MULTIPLY":
                    result = v1 * v2;
                    entity.setResult(result);
                    entity.setErrorMessage("Multiplication result: " + result);
                    break;

                case "DIVIDE":

                    if (v2 == 0) {
                        throw new RuntimeException("Cannot divide by zero");
                    }

                    result = v1 / v2;
                    entity.setResult(result);
                    entity.setErrorMessage("Division result: " + result);
                    break;

                case "COMPARE":

                    if (v1 > v2) {
                        entity.setResult(1.0);
                        entity.setErrorMessage(dto.getValue1() + " " + dto.getUnit1()
                                + " is greater than " + dto.getValue2() + " " + dto.getUnit2());
                    } else if (v1 < v2) {
                        entity.setResult(-1.0);
                        entity.setErrorMessage(dto.getValue1() + " " + dto.getUnit1()
                                + " is less than " + dto.getValue2() + " " + dto.getUnit2());
                    } else {
                        entity.setResult(0.0);
                        entity.setErrorMessage("Both quantities are equal");
                    }
                    break;

                case "CONVERT":
                    entity.setResult(v1);
                    entity.setErrorMessage("Converted value in base unit: " + v1);
                    break;

                default:
                    throw new RuntimeException("Invalid operation");
            }

            entity.setError(false);

        } catch (Exception e) {
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
        }

        return repository.save(entity);
    }

    public List<QuantityMeasurementEntity> getAll() {
        return repository.findAll();
    }

    //MAIN CONVERSION METHOD
    private double convertToBase(double value, String unit, String type) {

        switch (type.toLowerCase()) {

            //LENGTH (base = meter)
            case "length":
                switch (unit.toLowerCase()) {
                    case "km":
                    case "kilometer":
                        return value * 1000;
                    case "meter":
                    case "m":
                        return value;
                    case "cm":
                        return value / 100;
                }

            // WEIGHT (base = gram)
            case "weight":
                switch (unit.toLowerCase()) {
                    case "kg":
                        return value * 1000;
                    case "gram":
                    case "g":
                        return value;
                }

            //VOLUME (base = liter)
            case "volume":
                switch (unit.toLowerCase()) {
                    case "liter":
                    case "l":
                        return value;
                    case "ml":
                        return value / 1000;
                }

            //TEMPERATURE (base = Celsius)
            case "temperature":
                switch (unit.toLowerCase()) {
                    case "c":
                        return value;
                    case "f":
                        return (value - 32) * 5 / 9;
                    case "k":
                        return value - 273.15;
                }

            default:
                throw new RuntimeException("Invalid measurement type or unit");
        }
    }
}