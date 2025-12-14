package com.gsb.gestion.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.gsb.gestion.model.Comptes;
import com.gsb.gestion.model.TypeDeCompte;
import com.gsb.gestion.dao.ComptesDao;

public class ComptesService {

    private ComptesDao compteRepo;
    private int compteurNumero = 1;

    public ComptesService() throws SQLException {
        this.compteRepo = new ComptesDao();
    }

    public void creatAcc(Comptes compte) throws SQLException {
        compteRepo.insertCompte(compte);
    }

    public String generateNumAcc() {
        return "CPT" + String.format("%05d", compteurNumero++);
    }

    public void initilizationSolde(String numeroDeCompte, double montant) {
        compteRepo.updateSoldeAcc(numeroDeCompte, montant);
    }

    public List<TypeDeCompte> ListTypeAcc() {
        return Arrays.asList(TypeDeCompte.values());
    }

    public List<Comptes> searchAcc() {
        return compteRepo.selectAll();
    }

    public Comptes searchAccByNum(String numeroDeCompte) {
        return compteRepo.selectAccByNum(numeroDeCompte);
    }
}
