package com.pdf_generator.controller;

import com.pdf_generator.services.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/pdf")
@RestController
public class PdfController {
    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf() {

            // Deve ser passado o mesmo nome do arquivo HTML que será processado
            byte[] pdfBytes = pdfService.generatePdf("report");
            HttpHeaders headers = new HttpHeaders();

            // Define o nome do arquivo que será baixado
            headers.add("Content-Disposition", "attachment; filename=Relatorio.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
