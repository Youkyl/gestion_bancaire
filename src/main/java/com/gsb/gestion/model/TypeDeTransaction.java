package com.gsb.gestion.model;

public enum TypeDeTransaction {
    
    DEPOT("Entrée"),
    RETRAIT("Sortie");
    
    private String label;
    
    TypeDeTransaction(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
