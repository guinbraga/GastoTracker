package com.example.gastotracker.enums;

public enum Categorias {
    ALIMENTACAO("Alimentação"),
    TRANSPORTE("Transporte"),
    MORADIA("Moradia"),
    SAUDE("Saúde"),
    LAZER("Lazer"),
    OUTROS("Outros");

    private final String displayName;

    Categorias(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}