package com.joyful.serviceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.joyful.service.CsvImportService;
import com.joyful.util.CsvImportResult;

@Service
public class CsvImportServiceImpl implements CsvImportService {

	@Override
	public CsvImportResult importCsv(MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
    // implement importCsv()
}
