package com.joyful.util;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvImportResult {
    private int totalRows;
    private int successRows;
    private int skippedRows;
    private List<RowError> errors = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RowError {
        private int rowNumber;
        private String reason;
    }

    public void addError(int row, String reason) {
        errors.add(new RowError(row, reason));
        skippedRows++;
    }

    public void incrementSuccess() {
        successRows++;
    }

    public void incrementTotal() {
        totalRows++;
    }
} 
