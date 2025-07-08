package com.azienda.controller;

import com.azienda.jpa.entity.Progetto;
import com.azienda.service.interfaces.ProgettoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping({"crudProgetto"})
public class ControllerProgetto {

    @Autowired
    private ProgettoService progettoService;

    @ResponseBody
    @RequestMapping(value = "/insertProgetto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertProgetto(@RequestBody String request) {
        JSONObject json;
        try {
            json = new JSONObject(request);

        } catch (Exception e) {
            return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
        }

        try {
            if (json.has("nome") && json.has("dataInizio") && json.has("dataFine") && json.has("budget")) {
                Progetto p = progettoService.convertJsonObject(json);
                progettoService.insertProgetto(p);
                return new ResponseEntity<>(p, HttpStatus.CREATED);
            } else
                return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'inserimento" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findByName" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByNome(@RequestBody String request) {
        JSONObject json;
        try {
            json = new JSONObject(request);

        } catch (Exception e) {
            return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
        }

        try {
            if (json.has("nome")){
                Optional<Progetto> p = progettoService.findByNome(json.getString("nome"));
                if (p != null)
                    return new ResponseEntity<>(p, HttpStatus.OK);
                else
                    return new ResponseEntity<>("Progetto non trovato", HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'inserimento" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findByBudget" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByBudget(@RequestBody String request) {
        JSONObject json;
        try {
            json = new JSONObject(request);

        } catch (Exception e) {
            return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
        }

        try {
            if (json.has("budget")){
                double budget = json.getDouble("budget");
                List<Progetto> p = progettoService.findProgettoByBudget(budget);
                if (p.size() > 0)
                    return new ResponseEntity<>(p, HttpStatus.OK);
                else
                    return new ResponseEntity<>("Progetto non trovato", HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'inserimento" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/deleteProgetto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProgetto(@RequestBody String request) {
        JSONObject json;
        try {
            json = new JSONObject(request);

        } catch (Exception e) {
            return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
        }
        try{
            if (json.has("nome")) {
                String nome = json.getString("nome");
                Optional<Progetto> p = progettoService.findByNome(nome);
                progettoService.deleteProgetto(p);
                return new ResponseEntity<>(p, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'inserimento" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
