package com.pblgllgs.jasper;

import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class Sb3JasperreportsApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(Sb3JasperreportsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Sb3JasperreportsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            String destinationPath =
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "static" + File.separator +
                            "ReportGenerated.pdf";
            String templatePath =
                            "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "templates" + File.separator +
                            "report" + File.separator +
                            "Report.jrxml";

            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("VoucherId", UUID.randomUUID().toString());
            parameters.put("CurrentDate",formatter.format(time));
            parameters.put("AmountPaid", new BigDecimal(99999));
            parameters.put("PaymentMethod", "Mastercard");
            parameters.put("ParentName","Rafael Gallegos Flores");
            parameters.put("ChildName","Pablo Gallegos González");
            parameters.put("imageDir","classpath:/static/images/");

            JasperReport report = JasperCompileManager.compileReport(templatePath);
            JasperPrint print = JasperFillManager.fillReport(report,parameters,new JREmptyDataSource());

            JasperExportManager.exportReportToPdfFile(print, destinationPath);
            LOGGER.info("Report generated successfully!!");
        };
    }

}
