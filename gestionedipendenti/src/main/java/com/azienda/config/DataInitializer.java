package com.azienda.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;
import com.azienda.service.interfaces.PermessoService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PermessoService permessoService;

    public DataInitializer(PermessoService permessoService) {
        this.permessoService = permessoService;
    }

    @Override
    public void run(String... args) {

        for (TipoPermesso tipo : TipoPermesso.values()) {

            Permesso permessoEsistente = permessoService.findByTipoPermesso(tipo);

            if (permessoEsistente == null) {
                Permesso nuovo = new Permesso(tipo);
                permessoService.savePermesso(nuovo);
            }
        }

        System.out.println("✔ Permessi inizializzati correttamente");
    }
}