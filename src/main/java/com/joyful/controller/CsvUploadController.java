package com.joyful.controller;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.joyful.service.CsvImportService;
import com.joyful.util.CsvImportResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CsvUploadController {

	private final CsvImportService csvImportService;

	@PostMapping("/upload-csv")
	public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
		}

		if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv")) {
			return ResponseEntity.badRequest().body(Map.of("error", "Only .csv files are allowed"));
		}

		try {
			CsvImportResult result = csvImportService.parseAndImport(file);

			// ✅ Set success based on number of successful rows
			result.setSuccess(result.getSuccessRows() > 0);

			return ResponseEntity.ok(result); // this returns JSON
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
		}
	}

	@GetMapping("/template")
	public ResponseEntity<?> downloadTemplate() {
		// TODO: Return a sample CSV file
		return ResponseEntity.ok("Template endpoint not implemented yet");
	}
}
