package com.example.vehicle.controller;

import com.example.vehicle.model.Vehicle;
import com.example.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> getAllVehicles(@RequestHeader("Authorization") String token) {
        return vehicleService.getAllVehicles(token);
    }

    @GetMapping("/{id}")
    public Vehicle getVehicleById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return vehicleService.getVehicleById(token, id);
    }

    @PostMapping
    public Vehicle addVehicle(@RequestHeader("Authorization") String token, @RequestBody Vehicle vehicle) {
        return vehicleService.addVehicle(token, vehicle);
    }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(token, id, vehicle);
    }

    @DeleteMapping("/{id}")
    public String deleteVehicle(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        vehicleService.deleteVehicle(token, id);
        return "Vehicle deleted successfully!";
    }
}
