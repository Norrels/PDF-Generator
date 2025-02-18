package com.pdf_generator.controller;

import com.pdf_generator.services.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequestMapping("/pdf")
@RestController
public class PdfController {
    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf() {

            List<Map<String, Object>> itens = List.of(
                    Map.of("produto", "Notebook", "quantidade", 2, "preco", "R$ 5000"),
                    Map.of("produto", "Mouse", "quantidade", 5, "preco", "R$ 50"),
                    Map.of("produto", "Teclado", "quantidade", 3, "preco", "R$ 100")
            );

            Map<String, Object> data = Map.of(
                    "dataAtual", LocalDate.now().toString(),
                    "itens", itens
            );

            byte[] pdfBytes = pdfService.generatePdf("documento", data);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=relatorio.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

    }
}
