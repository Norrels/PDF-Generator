package com.pdf_generator.services;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(String templateName) {

        Context context = new Context();
        context.setVariable("employerName", "John Doe da Silva");
        context.setVariable("employerRole", "Vendedor Pleno");
        context.setVariable("employerDepartment", "Vendas");
        context.setVariable("dataHora", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        List<Map<String, String>> vendasMensais = List.of(
                Map.of("category", "Eletrônicos", "total", "R$ 90.000,00", "qtd", "90", "comission", "R$ 1.500,00"),
                Map.of("category", "Móveis", "total", "R$ 79.200,00", "qtd", "132", "comission", "R$ 1.320,00"),
                Map.of("category", "Vestuário", "total", "R$ 54.000,00", "qtd", "108", "comission", "R$ 900,00"),
                Map.of("category", "Acessórios", "total", "R$ 48.000,00", "qtd", "192", "comission", "R$ 800,00")
        );

        context.setVariable("itens", vendasMensais);

        String html = templateEngine.process(templateName, context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ConverterProperties properties = new ConverterProperties();
            properties.setBaseUri("src/main/resources/static/");
            HtmlConverter.convertToPdf(html, outputStream, properties);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}
