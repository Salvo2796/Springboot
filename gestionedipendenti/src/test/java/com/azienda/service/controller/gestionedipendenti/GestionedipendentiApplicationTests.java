package com.azienda.service.controller.gestionedipendenti;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb"
})
@ActiveProfiles("test")
class GestionedipendentiApplicationTests {

    @Test
    void contextLoads() {
    }

}