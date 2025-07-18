package com.joyful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.joyful.service.CsvImportService;
import com.joyful.util.CsvImportResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/upload-csv")
@RequiredArgsConstructor
public class CsvUploadController {

	private final CsvImportService csvImportService;

	@PostMapping
	public ResponseEntity<?> handleCsvUpload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("CSV file is required");
		}

		if (!file.getOriginalFilename().endsWith(".csv") && !file.getOriginalFilename().endsWith(".txt")) {
			return ResponseEntity.badRequest().body("Only .csv or .txt files are allowed");
		}

		if (file.getSize() > 10 * 1024 * 1024) {
			return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
		}

		try {
			CsvImportResult result = csvImportService.importCsv(file);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Import failed: " + e.getMessage());
		}
	}

	@GetMapping("/template")
	public ResponseEntity<?> downloadTemplate() {
		// TODO: Return a sample CSV file
		return ResponseEntity.ok("Template endpoint not implemented yet");
	}
} 
