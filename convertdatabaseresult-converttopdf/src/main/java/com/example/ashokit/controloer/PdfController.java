package com.example.ashokit.controloer;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ashokit.entity.PdfEntity;
import com.example.ashokit.pdfgenerator.PdfGenerator;
import com.example.ashokit.repository.PdfRepository;
import com.example.ashokit.service.PdfService;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PdfController {

	@Autowired
	private PdfService pdfservice;
	@Autowired
	private PdfRepository pdfRepository;

	@GetMapping(value = "/getpage")
	public String showPage() {
		return "welcome to the pdf";
	}

	@GetMapping(value = "/getallData")
	public ResponseEntity<List<PdfEntity>> getData() {
		List<PdfEntity> allData = pdfservice.getAllData();
		return new ResponseEntity<List<PdfEntity>>(allData, HttpStatus.OK);
	}

	@GetMapping("/export-to-pdf")
	public void generatePdfFile(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		String currentDateTime = dateFormat.format(new java.util.Date());
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=student" + currentDateTime + ".pdf";
		response.setHeader(headerkey, headervalue);
		List<PdfEntity> listofStudents = pdfservice.getAllData();
		PdfGenerator generator = new PdfGenerator();
		generator.generate(listofStudents, response);
	}
}
