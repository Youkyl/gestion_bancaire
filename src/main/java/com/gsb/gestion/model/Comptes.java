package com.gsb.gestion.model;

import java.util.List;

public class Comptes {
    private String id;
    private String numeroDeCompte;
    private double solde;
    private List<Transaction> transactions;
    private TypeDeCompte type;
    
    private Integer dureeDeblocage; 
    
    public Comptes() {
    }

    public Comptes(String id, String numeroDeCompte, double solde, TypeDeCompte type, List<Transaction> transactions) {
        this.id = id;
        this.numeroDeCompte = numeroDeCompte;
        this.solde = solde;
        this.type = type;
        this.transactions = transactions;
    }

    public Comptes(String numeroDeCompte, double solde, TypeDeCompte type) {
        this.numeroDeCompte = numeroDeCompte;
        this.solde = solde;
        this.type = type;
    }
    
    public Comptes(String id, String numeroDeCompte, double solde, TypeDeCompte type, Integer dureeDeblocage, List<Transaction> transactions) {
        this(id, numeroDeCompte, solde, type, transactions);
        this.dureeDeblocage = dureeDeblocage;
    }

    public Comptes( String numeroDeCompte, double solde, TypeDeCompte type, Integer dureeDeblocage) {
        this(numeroDeCompte, solde, type);
        this.dureeDeblocage = dureeDeblocage;
    }
    

    public String getId() {
        return id;
    }
    
    public String getNumeroDeCompte() {
        return numeroDeCompte;
    }
    
    public double getSolde() {
        return solde;
    }
    
    public void setSolde(double solde) {
        this.solde = solde;
    }
    
    public TypeDeCompte getType() {
        return type;
    }
    
    public Integer getDureeDeblocage() {
        return dureeDeblocage;
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public boolean isCompteEpargne() {
        return type == TypeDeCompte.EPARGNE;
    }
    
    public boolean isCompteCheque() {
        return type == TypeDeCompte.CHEQUE;
    }
    
    public double getFraisTransaction(double montant) {
        
        if (isCompteCheque()) {
            return montant * 0.8;
        }
        return 0;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setNumeroDeCompte(String numeroDeCompte) {
        this.numeroDeCompte = numeroDeCompte;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setType(TypeDeCompte type) {
        this.type = type;
    }

    public void setDureeDeblocage(Integer dureeDeblocage) {
        this.dureeDeblocage = dureeDeblocage;
    }

    @Override
    public String toString() {
        String info = "ID: " + id + ", Numéro: " + numeroDeCompte + 
                     ", Type: " + type + ", Solde: " + solde + " FCFA";
        if (dureeDeblocage != null) {
            info += ", Durée de blocage: " + dureeDeblocage + " mois";
        }
        if (isCompteCheque()) {
            info += ", Frais par transaction: 80%";
        }
        return info;
    }
}