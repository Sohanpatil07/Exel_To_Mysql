package com.exeltomysql.service;

import com.exeltomysql.entity.Employee;
import com.exeltomysql.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

@Service
public class ExcelService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void saveEmployeesFromExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            if (currentRow.getRowNum() == 0) { // Skip header row
                continue;
            }

            Employee employee = new Employee();
            employee.setName(currentRow.getCell(0).getStringCellValue());
            employee.setPosition(currentRow.getCell(1).getStringCellValue());
            employee.setSalary(currentRow.getCell(2).getNumericCellValue());

            employeeRepository.save(employee);
        }

        workbook.close();
    }
}
