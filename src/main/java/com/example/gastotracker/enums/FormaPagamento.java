package com.example.gastotracker.enums;

public enum FormaPagamento {
    CREDITO("Cartão de Crédito"),
    DEBITO_PIX("Pix/Débito");

    private final String displayName;

    FormaPagamento(String displayName) {this.displayName = displayName;}

    public String getDisplayName() {return displayName;}
}
