package com.example.vehicle.service;

import com.example.vehicle.model.User;
import com.example.vehicle.model.Vehicle;
import com.example.vehicle.repository.UserRepository;
import com.example.vehicle.repository.VehicleRepository;
import com.example.vehicle.config.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    private User getUserFromToken(String token) {
        String email = JwtTokenUtil.extractEmail(token.replace("Bearer ", ""));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Vehicle> getAllVehicles(String token) {
        String email = JwtTokenUtil.extractEmail(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return vehicleRepository.findByUserId(user.getId());
    }

    public Vehicle getVehicleById(String token, Long id) {
        User user = getUserFromToken(token);
        return vehicleRepository.findById(id)
                .filter(vehicle -> vehicle.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Vehicle not found or access denied"));
    }

    public Vehicle addVehicle(String token, Vehicle vehicle) {
        User user = getUserFromToken(token);
        vehicle.setUser(user);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(String token, Long id, Vehicle updatedVehicle) {
        User user = getUserFromToken(token);

        Vehicle existingVehicle = vehicleRepository.findById(id)
                .filter(vehicle -> vehicle.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Vehicle not found or access denied"));

        existingVehicle.setName(updatedVehicle.getName());
        existingVehicle.setType(updatedVehicle.getType());
        existingVehicle.setPlate(updatedVehicle.getPlate());
        existingVehicle.setYear(updatedVehicle.getYear());
        existingVehicle.setMileage(updatedVehicle.getMileage());
        existingVehicle.setFuel(updatedVehicle.getFuel());
        existingVehicle.setTransmission(updatedVehicle.getTransmission());
        existingVehicle.setColor(updatedVehicle.getColor());

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(String token, Long id) {
        User user = getUserFromToken(token);

        Vehicle vehicle = vehicleRepository.findById(id)
                .filter(v -> v.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Vehicle not found or access denied"));

        vehicleRepository.delete(vehicle);
    }
}
