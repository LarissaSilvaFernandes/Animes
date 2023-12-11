package com.anime.model.enums;

public enum AvaliacaoAnime {
    RUIM(0),
    BOM(1),
    EXCELENTE(2);

    private int valor;

    AvaliacaoAnime(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
