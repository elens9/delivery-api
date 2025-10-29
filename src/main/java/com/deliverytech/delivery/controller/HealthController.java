package com.deliverytech.delivery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health(){
        return Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now().toString(),
                "service", "Delivery API",
                "javaVersion", System.getProperty("java.version")
        );
    }

    //endpoint com informacoes basicas do projeto
    @GetMapping("/info")
    public  AppInfo info(){
        return new AppInfo(
                "Delivery Tech API",
                "1.0.0",
                "Elen Sant'Anna Filipeto",
                "JDK 21",
                "Spring Boot 3.5.7"
        );
    }

    //record para demonstrar recurso do Java 14+ (disponivel no JDK 21)
    public record AppInfo(
            String application,
            String version,
            String developer ,
            String javaVersion,
            String framework
    ){}
}
