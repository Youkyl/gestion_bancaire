package com.gsb.gestion.model;

import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private double montant;
    private TypeDeTransaction type;
    private String numeroDeCompte = new Comptes().getNumeroDeCompte(); 
    private Comptes compte;
    private LocalDateTime date;
    private double frais;
    
    public Transaction(String id, double montant, TypeDeTransaction type, Comptes compte, LocalDateTime date) {
        this.id = id;
        this.montant = montant;
        this.type = type;
        this.compte = compte;
        this.date = date;
    }

    public Transaction(double montant, TypeDeTransaction type, Comptes compte, LocalDateTime date) {
        this.montant = montant;
        this.type = type;
        this.compte = compte;
        this.date = date;
    }
    
    public Transaction(double montant, double frais, TypeDeTransaction type, String numeroDeCompte) {
        this.montant = montant;
        this.frais = frais;
        this.type = type;
        this.numeroDeCompte = numeroDeCompte;
    }

    public Transaction(){}
    

    public double getFrais() {
        return frais;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setType(TypeDeTransaction type) {
        this.type = type;
    }

    public void setCompte(Comptes compte) {
        this.compte = compte;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public String getId() {
        return id;
    }
    
    public double getMontant() {
        return montant;
    }
    
    public TypeDeTransaction getType() {
        return type;
    }
    
    public String getNumeroDeCompte() {

        if (compte != null) {

           return compte.getNumeroDeCompte();
           
        } else{

            System.err.println("Erreur, aucun numero de comote trouve");
        }

        return null;
    }

    public Comptes getCompte() {
        return compte;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Type: " + type + ", Montant: " + montant + 
               " FCFA, Date: " + date;
    }
}
