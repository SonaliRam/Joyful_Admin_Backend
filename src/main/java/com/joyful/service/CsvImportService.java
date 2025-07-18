package com.joyful.service;

import org.springframework.web.multipart.MultipartFile;

import com.joyful.util.CsvImportResult;

public interface CsvImportService {
    CsvImportResult importCsv(MultipartFile file) throws Exception;
    CsvImportResult parseAndImport(MultipartFile file);
}
