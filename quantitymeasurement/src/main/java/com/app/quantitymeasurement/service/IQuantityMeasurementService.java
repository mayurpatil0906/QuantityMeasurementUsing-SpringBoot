package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.dto.QuantityDTO;
import com.app.quantitymeasurement.model.dto.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare(QuantityDTO dto);

    QuantityMeasurementDTO convert(QuantityDTO dto);

    QuantityMeasurementDTO add(QuantityDTO dto);

    QuantityMeasurementDTO subtract(QuantityDTO dto);

    QuantityMeasurementDTO multiply(QuantityDTO dto);

    QuantityMeasurementDTO divide(QuantityDTO dto);

    List<QuantityMeasurementDTO> getOperationHistory(String operation);

    List<QuantityMeasurementDTO> getMeasurementsByType(String type);

    long getOperationCount(String operation);

    List<QuantityMeasurementDTO> getErrorHistory();
}