package com.example.vehicle.service;

import com.example.vehicle.model.Vehicle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CsvExportService {
    public byte[] generateCsv(List<Vehicle> vehicles) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Vehicles");

            // Header Row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Model", "Type", "Plate", "Year", "Mileage", "Fuel", "Transmission", "Color"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            // Data Rows
            int rowNum = 1;
            for (Vehicle vehicle : vehicles) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(vehicle.getId());
                row.createCell(1).setCellValue(vehicle.getName());
                row.createCell(2).setCellValue(vehicle.getModel());
                row.createCell(3).setCellValue(vehicle.getType());
                row.createCell(4).setCellValue(vehicle.getPlate());
                row.createCell(5).setCellValue(vehicle.getYear());
                row.createCell(6).setCellValue(vehicle.getMileage());
                row.createCell(7).setCellValue(vehicle.getFuel());
                row.createCell(8).setCellValue(vehicle.getTransmission());
                row.createCell(9).setCellValue(vehicle.getColor());
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }
}
