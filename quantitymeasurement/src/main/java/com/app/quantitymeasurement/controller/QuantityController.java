package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.dto.QuantityDTO;
import com.app.quantitymeasurement.model.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.service.QuantityService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quantity")
public class QuantityController {

    private final QuantityService service;

    public QuantityController(QuantityService service) {
        this.service = service;
    }

    @PostMapping("/calculate")
    public QuantityMeasurementEntity calculate(
            @Valid @RequestBody QuantityDTO dto) {
        return service.calculate(dto);
    }

    @GetMapping("/history")
    public List<QuantityMeasurementEntity> getAll() {
        return service.getAll();
    }
    @GetMapping("/success")
    public String success(){
        return "success";
    }
}
