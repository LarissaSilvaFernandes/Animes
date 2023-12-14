package com.anime.exception;

import java.util.UUID;

public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException(UUID id) {
        super("NÃO ENCONTRADO: " + id);
    }
}
