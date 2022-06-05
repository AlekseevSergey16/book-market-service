package com.salekseev.booksmarket.controller;

import com.salekseev.booksmarket.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public byte[] getBooksSoldForMonth() {
        return reportService.exportBooksForMonth();
    }

}
