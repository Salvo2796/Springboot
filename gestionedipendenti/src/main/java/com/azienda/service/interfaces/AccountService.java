package com.azienda.service.interfaces;

import com.azienda.jpa.entity.Account;
import org.json.JSONObject;

import java.util.List;

public interface AccountService {
    public void insertAccount(Account a);

    public Account convertJSONAccount(JSONObject j);

    public List<Account> findAllAccount();

    public Account findByEmail(String email);

    public void deleteAccount(Account a);

    public void updateAccount(Account a);

    public Account convertJSONAccountConDipendente(JSONObject j);

    public Account createAccountWithPermesso(Account account);

}
