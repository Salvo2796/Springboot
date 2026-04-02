# Gestione Dipendenti - Spring Boot

## 📌 Descrizione
Applicazione backend sviluppata con Java e Spring Boot per la gestione dei dipendenti tramite API REST.

## 🚀 Tecnologie utilizzate
- Java
- Spring Boot
- Spring Data JPA
- H2 Database (in-memory)
- REST API
- Maven

## ▶️ Come avviare il progetto
1. Clonare il repository
2. Aprire il progetto
3. Avviare l'applicazione con:
   mvn spring-boot:run


## 🗄️ Accesso al database H2
Aprire il browser e andare su:
http://localhost:8080/h2-console

Inserire i seguenti dati:
- JDBC URL: jdbc:h2:mem:testdb
- User: sa
- Password: (vuoto)

## 📡 API disponibili
# Dipendente
- POST /crudDipendente/insertDipendente → Inserisce un nuovo dipendente senza account.

- POST /crudDipendente/insertDipendenteAccount → Inserisce un nuovo dipendente con account e permessi.

- GET /crudDipendente/findAllDipendente → Restituisce tutti i dipendenti.

- GET /crudDipendente/findByCf → Cerca un dipendente tramite codice fiscale.

- GET /crudDipendente/findByStipendio → Cerca dipendenti con uno stipendio specifico.

- GET /crudDipendente/findByStipendioAndDataDiAssunzione → Cerca dipendenti filtrando per stipendio e data di assunzione.

- DELETE /crudDipendente/deleteDipendente → Elimina un dipendente tramite codice fiscale.

- PUT /crudDipendente/updateDipendente/{cf} → Aggiorna i dati di un dipendente tramite codice fiscale.

- PUT /crudDipendente/aggiungiProgetto/{cf}/{progetto} → Aggiunge un progetto a un dipendente tramite codice fiscale.

# ACCOUNT
- POST /crudAccount/insertAccount → Inserisce un nuovo account (con eventuale dipendente e permesso).

- GET /crudAccount/findAllAccount → Restituisce tutti gli account presenti.

- GET /crudAccount/findByEmail → Cerca un account tramite email.

- DELETE /crudAccount/deleteAccount → Elimina un account tramite email.

- PUT /crudAccount/updateAccount/{email} → Aggiorna i dati di un account e del dipendente associato tramite email.

# PERMESSO
- GET /crudPermesso/findByPermesso → Cerca gli account associati a un determinato tipo di permesso.

# PROGETTO
- POST /crudProgetto/insertProgetto → Inserisce un nuovo progetto.

- POST /aggiungiProgettoADipendente → Abbina un progetto ad un dipendente

- GET /crudProgetto/findByName → Cerca un progetto tramite nome e restituisce i dipendenti associati.

- GET /crudProgetto/findByBudget → Cerca progetti con un determinato budget.

- DELETE /crudProgetto/deleteProgetto → Elimina un progetto tramite nome.