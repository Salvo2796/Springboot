package com.azienda.controller;

import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.Dipendente;
import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;
import com.azienda.service.interfaces.AccountService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping({ "crudAccount" })
public class ControllerAccount {

    @Autowired
    private AccountService accountService;

    @ResponseBody
    @RequestMapping(value = "/insertAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertAccount(@RequestBody String request) {
        JSONObject json;
        try {
            json = new JSONObject(request);
        } catch (Exception e) {
            return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
        }

        try {
            if (json.has("username") && json.has("pass") && json.has("email")) {
                Account account = accountService.convertJSONAccountConDipendente(json);

                Account savedAccount = accountService.createAccountWithPermesso(account);

                return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'inserimento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/findAllAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllAccount() {

        try {
            List<Account> account = accountService.findAllAccount();
            if (!account.isEmpty()) {
                return ResponseEntity.ok(account);
            } else {
                return new ResponseEntity<>("Lista non trovata", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante la ricerca" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByEmail(@RequestBody String request) {
        JSONObject json;
        try {
            json = new JSONObject(request);
        } catch (Exception e) {
            return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
        }

        try {
            if (json.has("email")) {

                String email = json.getString("email");
                Account a = accountService.findByEmail(email);
                if (a != null)
                    return ResponseEntity.ok(a);
                else
                    return ResponseEntity.ok("Nessun account presente");
            } else
                return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante la ricerca email" + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/deleteAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAccount(@RequestBody String request) {
        JSONObject json;
        try {
            json = new JSONObject(request);
        } catch (Exception e) {
            return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
        }

        try {
            if (json.has("email")) {
                String email = json.getString("email");
                Account acc = accountService.findByEmail(email);
                if (acc != null) {
                    accountService.deleteAccount(acc);
                    return ResponseEntity.ok("Account eliminato");
                } else {
                    return ResponseEntity.ok("Nessun account presente");
                }
            } else {
                return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'eliminazione" + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/updateAccount/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAccount(@PathVariable String email, @RequestBody String request) {
        JSONObject json;
        try {
            json = new JSONObject(request);
        } catch (Exception e) {
            return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
        }

        try {
            Account acc = accountService.findByEmail(email);
            if (acc != null) {
                if (json.has("username"))
                    acc.setUsername(json.getString("username"));
                if (json.has("email"))
                    acc.setEmail(json.getString("email"));
                if (json.has("pass"))
                    acc.setPass(json.getString("pass"));

                if (json.has("tipo_di_permesso")) {
                    String tipoPermessoStr = json.getString("tipo_di_permesso").toUpperCase();
                    TipoPermesso tipoPermesso;
                    try {
                        tipoPermesso = TipoPermesso.valueOf(tipoPermessoStr);
                    } catch (IllegalArgumentException e) {
                        return new ResponseEntity<>("Tipo di permesso non valido", HttpStatus.BAD_REQUEST);
                    }

                    Permesso permesso = new Permesso(tipoPermesso);
                    acc.setPermesso(permesso);
                }

                if(json.has("dipendente")) {

                    JSONObject dipJ = json.getJSONObject("dipendente");
                    Dipendente dipendente = acc.getDipendente();
                    
                    if (dipJ.has("nome")) 
                        dipendente.setNome(dipJ.getString("nome"));
                    
                    if (dipJ.has("cognome")) 
                        dipendente.setCognome(dipJ.getString("cognome"));
                    
                    if (json.has("data_di_nascita")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                dipendente.setDataDiNascita(LocalDate.parse(json.get("data_di_nascita").toString(), formatter));
            }

            if (json.has("data_di_assunzione")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                dipendente.setDataDiAssunzione(LocalDate.parse(json.get("data_di_assunzione").toString(), formatter));
            }

            if (json.has("cf"))
                dipendente.setCf(json.getString("cf"));

            if (json.has("stipendio"))
                dipendente.setStipendio(json.getDouble("stipendio"));
            }
                accountService.updateAccount(acc);
                return ResponseEntity.ok("Account aggiornato");
            } else {
                return ResponseEntity.ok("Nessun account presente");
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'update dell'account" + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
