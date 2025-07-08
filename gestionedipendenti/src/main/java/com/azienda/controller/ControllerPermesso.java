package com.azienda.controller;

import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.Dipendente;
import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;
import com.azienda.service.interfaces.PermessoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping({ "crudPermesso" })
public class ControllerPermesso {

    @Autowired
    private PermessoService ps;

    @GetMapping(value = "/findByPermesso",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByPermesso(@RequestBody String request){

        JSONObject json;
        try {
            json = new JSONObject(request);
        } catch (Exception e) {
            return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
        }

        try {
            if (json.has("tipo_di_permesso")) {
                String tipoPermessoStr = json.getString("tipo_di_permesso").toUpperCase();
                TipoPermesso tipoPermesso;
                try {
                    tipoPermesso = TipoPermesso.valueOf(tipoPermessoStr);
                } catch (IllegalArgumentException e) {
                    return new ResponseEntity<>("Tipo di permesso non valido: " + tipoPermessoStr, HttpStatus.BAD_REQUEST);
                }

                List<Account> permessi = ps.findDipendenteByPermesso(tipoPermesso);

                if (permessi != null && !permessi.isEmpty()) {
                    return ResponseEntity.ok(permessi);
                } else {
                    return ResponseEntity.ok("Nessun permesso presente per il tipo specificato");
                }
            } else {
                return new ResponseEntity<>("Campo mancante", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante la ricerca: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
