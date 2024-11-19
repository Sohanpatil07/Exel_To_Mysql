package com.exeltomysql;

import com.exeltomysql.service.ExcelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ExelToMysqlApplicationTests {

	@Autowired
	private ExcelService service;

	@Test
	void contextLoads() {
	}
	@Test
	public void testUploadExcelFile_Success() throws Exception {
		// Mocking a valid Excel file
		MockMultipartFile mockFile = new MockMultipartFile(
				"file",
				"employees.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"dummy content".getBytes()
		);

		// Mock service to do nothing
		doNothing().when(service).saveEmployeesFromExcel(any());

		// Perform the mock request and verify response
		mockMvc.perform(multipart("/upload-excel")
						.file(mockFile)
						.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk())
				.andExpect(content().string("Data successfully uploaded to the database."));
	}



}
