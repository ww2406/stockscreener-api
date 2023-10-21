package com.ww.stockscreener.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {
    private ArrayList<String> readExcelFile(MultipartFile file, String columnName) throws Exception {
        Workbook wb = WorkbookFactory.create(file.getInputStream());

        Sheet sheet = wb.getSheetAt(0);

        var headerRow = sheet.getRow(0);
        ArrayList<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(cell.getStringCellValue());
        }

        var selColIndex = headers.indexOf(columnName);
        if (selColIndex == -1) {
            throw new Exception("No matching column found in file.");
        }

        ArrayList<String> tickers = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            tickers.add(row.getCell(selColIndex).getStringCellValue());
        }

        return tickers;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<String>> parseFileForTickers(@RequestParam String columnName,
                                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data"), useParameterTypeSchema = true) @RequestParam("file") MultipartFile file) throws Exception {
        var contentType = file.getContentType();

        if (contentType == null) {
            throw new InvalidMimeTypeException("null", "A content-type must be provided.");
        }
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return ResponseEntity.ok(readExcelFile(file, columnName));
        } else {
            throw new InvalidMimeTypeException(contentType, "The provided content-type is not supported.");
        }
    }
}
