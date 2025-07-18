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
    private boolean success;
    private String message;
    private List<SkippedRow> skippedRowsList = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkippedRow {
        private int rowNumber;
        private String reason;
    }

    public void addSkippedRow(int row, String reason) {
        this.skippedRowsList.add(new SkippedRow(row, reason));
        skippedRows++;
    }

    public void incrementSuccess() {
        successRows++;
    }

    public void incrementTotal() {
        totalRows++;
    }
}
