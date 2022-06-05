package com.salekseev.booksmarket.service.impl;

import com.salekseev.booksmarket.model.Book;
import com.salekseev.booksmarket.model.BookReport;
import com.salekseev.booksmarket.repository.BookRepository;
import com.salekseev.booksmarket.service.ReportService;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final BookRepository bookRepository;

    public ReportServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @SneakyThrows
    @Override
    public byte[] exportBooksForMonth() {
        List<BookReport> books = bookRepository.findAllSoldByMonth();

        File file = ResourceUtils.getFile("classpath:report/booksForMonth.jrxml");

        String reportPath = "C:\\Users\\Never\\Documents\\sold-books-" + LocalDate.now() + ".pdf";

        JasperReport jasperReport = JasperCompileManager
                .compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(books);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "salekseev.book");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                jrBeanCollectionDataSource);

        // Export the report to a PDF file
        JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath);

        var bufferedInputStream = new BufferedInputStream(new FileInputStream(reportPath));

        return bufferedInputStream.readAllBytes();
    }
}
