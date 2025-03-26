package com.example.vehicle.controller;

import com.example.vehicle.config.JwtTokenUtil;
import com.example.vehicle.model.User;
import com.example.vehicle.model.Vehicle;
import com.example.vehicle.repository.UserRepository;
import com.example.vehicle.repository.VehicleRepository;
import com.example.vehicle.service.CsvExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/csv")
@CrossOrigin(origins = "https://vehicle-info-frontend.vercel.app", allowCredentials = "true")
public class CsvController {

    @Autowired
    private CsvExportService csvExportService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadCsv(@RequestHeader("Authorization") String token) throws IOException {
        // Extract email from JWT
        String email = JwtTokenUtil.extractEmail(token.replace("Bearer ", ""));

        // Fetch user ID using the extracted email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch vehicles belonging to this user
        List<Vehicle> vehicles = vehicleRepository.findByUserId(user.getId());
        byte[] csvData = csvExportService.generateCsv(vehicles);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vehicles.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(csvData);
    }
}


