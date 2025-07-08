package com.azienda.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.azienda.jpa.entity.Dipendente;
import com.azienda.service.interfaces.DipendenteService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping({"crudDipendente"})
public class Controller {
    @Autowired
    private DipendenteService dipendenteService;

    @ResponseBody
    @RequestMapping(value = "/insertDipendente", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertDipendente(@RequestBody String request) {
        JSONObject json;
        try {
			json = new JSONObject(request);

		} catch (Exception e) {
			return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
		}

		try {

			if (json.has("nome") && json.has("cognome")) {
				Dipendente d = dipendenteService.convertJSONDipendente(json);
				dipendenteService.insertDipendente(d);
				return new ResponseEntity<>(d, HttpStatus.CREATED);

			} else
				return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			return new ResponseEntity<>("Errore durante l'inserimento" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
    } 

	@RequestMapping(value = "/insertDipendenteAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> insertDipendenteAccount(@RequestBody String request) {
		JSONObject json;
		try {
			json = new JSONObject(request);
		} catch (Exception e) {
			return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
		}

		try {

			if (json.has("nome") && json.has("cognome") && json.has("cf") && json.has("data_di_nascita") && json.has("data_di_assunzione") && json.has("stipendio") && json.has("account")) {
				Dipendente d = dipendenteService.convertJSONDipendenteConAccount(json);
				dipendenteService.insertDipendenteAccount(d);
				return new ResponseEntity<>(d, HttpStatus.CREATED);
			} else
				return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			return new ResponseEntity<>("Errore durante l'inserimento" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @GetMapping(value = "/findAllDipendente", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAllDipendente() {
		try {
            
			List<Dipendente> dipendenti = dipendenteService.findAllDipendente();

			if (dipendenti.isEmpty()) {
				return new ResponseEntity<>("Dipendente non trovato", HttpStatus.NO_CONTENT); 

			} else {
				return ResponseEntity.ok(dipendenti); 
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Errore durante il recupero dati" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

    @GetMapping(value= "/findByCf", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByCf(@RequestBody String request) {
        JSONObject json;

		try {
			json=new JSONObject(request);

		} catch (Exception e) {
			return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
		}

		try {

			if(json.has("cf")) {
				Dipendente d=dipendenteService.findByCf(json.getString("cf"));

				if(d!=null)
					return ResponseEntity.ok(d);
				else
					return ResponseEntity.ok("Nessun dipendente presente");
			}else
				return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);

		}
		catch (Exception e) {
			return new ResponseEntity<>("Errore durante la ricerca cf" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@GetMapping(value = "/findByStipendio", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findByStipendio(@RequestBody String request) {
		JSONObject json;

		try{
			json=new JSONObject(request);
		}catch (Exception e) {
			return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
		}

		try {
			if (json.has("stipendio")){
				double stipendio = json.getDouble("stipendio");
				List<Dipendente> dipendenti = dipendenteService.findByStipendio(stipendio);

				if (dipendenti.isEmpty()) {
					return ResponseEntity.ok("Nessun dipendente presente");
				} else {
					return ResponseEntity.ok(dipendenti);
				}
			} else {
				return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Errore durante la ricerca stipendio" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/findByStipendioAndDataDiAssunzione", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findByStipendioAndDataDiAssunzione(@RequestBody String request) {
		JSONObject json;

		try{
			json=new JSONObject(request);
		}catch (Exception e) {
			return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
		}

		try {
			if (json.has("stipendio") && json.has("data_di_assunzione")) {

				double stipendio = json.getDouble("stipendio");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate dataDiAssunzione = LocalDate.parse(json.get("data_di_assunzione").toString(), formatter);

				List<Dipendente> dipendenti = dipendenteService.findByStipendioAndDataDiAssunzione(stipendio, dataDiAssunzione);
				if (dipendenti.isEmpty()) {
					return ResponseEntity.status(HttpStatus.OK).body("Nessun dipendente trovato con i criteri specificati");
				} else {
					return ResponseEntity.ok(dipendenti);
				}
			} else{
				return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Errore durante la ricerca stipendio e data di assunzione" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/deleteDipendente", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteDipendente(@RequestBody String request) {
		JSONObject json;
		try{
			json=new JSONObject(request);
		}catch (Exception e) {
			return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
		}

		try {
			if (json.has("cf")) {
				String cf = json.getString("cf");
				Dipendente dipendente = dipendenteService.findByCf(cf);
				if (dipendente != null) {
					dipendenteService.deleteDipendente(dipendente);
					return ResponseEntity.ok(dipendente);

				} else {
					return ResponseEntity.ok("Nessun dipendente presente");
				}
			} else {
				return new ResponseEntity<>("Campi mancanti", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Errore durante l'eliminazione" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateDipendente/{cf}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateDipendente(@PathVariable("cf") String codiceFiscale, @RequestBody String request) { //@PathVariable Estrae il valore {cf} dall'URL e lo assegna alla variabile codiceFiscale
		JSONObject json;
		try {
			json = new JSONObject(request);
		} catch (Exception e) {
			return new ResponseEntity<>("Formato JSON non valido", HttpStatus.BAD_REQUEST);
		}

		try {
			Dipendente daAggiornare = dipendenteService.findByCf(codiceFiscale);

			if (daAggiornare != null) {
				if (json.has("nome"))
					daAggiornare.setNome(json.getString("nome"));

				if (json.has("cognome"))
					daAggiornare.setCognome(json.getString("cognome"));

				if (json.has("data_di_nascita")) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					daAggiornare.setDataDiNascita(LocalDate.parse(json.get("data_di_nascita").toString(), formatter));
				}

				if (json.has("data_di_assunzione")) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					daAggiornare.setDataDiAssunzione(LocalDate.parse(json.get("data_di_assunzione").toString(), formatter));
				}

				if (json.has("cf"))
					daAggiornare.setCf(json.getString("cf"));

				if (json.has("stipendio"))
					daAggiornare.setStipendio(json.getDouble("stipendio"));

				if (json.has("account")) {

					JSONObject accJ = json.getJSONObject("account");// Prendo l'oggetto JSON relativo all'Account

					Account account = daAggiornare.getAccount();//recupero l'account dal dipendente
					if (accJ.has("username"))
						account.setUsername(accJ.getString("username"));
					if (accJ.has("pass"))
						account.setPass(accJ.getString("pass"));
					if (accJ.has("email"))
						account.setEmail(accJ.getString("email"));

					if (accJ.has("tipo_di_permesso")) {
						String tipoPermessoStr = accJ.getString("tipo_di_permesso").toUpperCase();

						try {
							TipoPermesso tipoPermesso = TipoPermesso.valueOf(tipoPermessoStr);
							// Crea un nuovo Permesso SENZA cercare nel database
							Permesso permesso = new Permesso(tipoPermesso);

							account.setPermesso(permesso);

						} catch (IllegalArgumentException e) {
							return new ResponseEntity<>("Tipo di permesso non valido: " + tipoPermessoStr, HttpStatus.BAD_REQUEST);
						}
					}

				}

				dipendenteService.updateDipendenteAccount(daAggiornare);
				return ResponseEntity.ok(daAggiornare);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dipendente non trovato");
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Errore durante l'aggiornamento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
