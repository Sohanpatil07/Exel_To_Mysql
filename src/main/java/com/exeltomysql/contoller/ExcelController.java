package com.exeltomysql.contoller;

import com.exeltomysql.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/upload-excel")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            excelService.saveEmployeesFromExcel(file);
            return ResponseEntity.status(HttpStatus.OK).body("Data successfully uploaded to the database.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload data: " + e.getMessage());
        }
    }
}
