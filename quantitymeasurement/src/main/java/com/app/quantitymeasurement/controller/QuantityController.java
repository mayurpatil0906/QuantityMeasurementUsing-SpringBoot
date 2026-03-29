package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.dto.QuantityDTO;
import com.app.quantitymeasurement.model.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.QuantityService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityController {

    private final QuantityService service;

    public QuantityController(QuantityService service) {
        this.service = service;
    }

    // ADD
    @PostMapping("/add")
    public QuantityMeasurementDTO add(@Valid @RequestBody QuantityDTO dto) {
        return service.add(dto);
    }

    // SUBTRACT
    @PostMapping("/subtract")
    public QuantityMeasurementDTO subtract(@Valid @RequestBody QuantityDTO dto) {
        return service.subtract(dto);
    }

    // MULTIPLY
    @PostMapping("/multiply")
    public QuantityMeasurementDTO multiply(@Valid @RequestBody QuantityDTO dto) {
        return service.multiply(dto);
    }

    // DIVIDE
    @PostMapping("/divide")
    public QuantityMeasurementDTO divide(@Valid @RequestBody QuantityDTO dto) {
        return service.divide(dto);
    }

    //COMPARE
    @PostMapping("/compare")
    public QuantityMeasurementDTO compare(@Valid @RequestBody QuantityDTO dto) {
        return service.compare(dto);
    }

    //CONVERT
    @PostMapping("/convert")
    public QuantityMeasurementDTO convert(@Valid @RequestBody QuantityDTO dto) {
        return service.convert(dto);
    }

    // HISTORY (ALL)
    @GetMapping("/history")
    public List<QuantityMeasurementDTO> getAll() {
        return service.getOperationHistory("ADD"); // you can customize
    }

    // HISTORY BY OPERATION
    @GetMapping("/history/{operation}")
    public List<QuantityMeasurementDTO> getByOperation(@PathVariable String operation) {
        return service.getOperationHistory(operation);
    }

    // ERROR HISTORY
    @GetMapping("/history/errors")
    public List<QuantityMeasurementDTO> getErrors() {
        return service.getErrorHistory();
    }

    // COUNT
    @GetMapping("/count/{operation}")
    public long getCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }
}