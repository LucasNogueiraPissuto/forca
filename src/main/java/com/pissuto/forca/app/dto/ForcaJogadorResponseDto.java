package com.pissuto.forca.app.dto;

import com.pissuto.forca.domain.ForcaJogoDomain;

import java.util.List;

public class ForcaJogadorResponseDto {
    private String id;
    private String email;

    private List<ForcaJogoResponseDto> forcaJogoDomains;

    public ForcaJogadorResponseDto(String email, ForcaJogoResponseDto forcaJogoDomains) {
        this.email = email;
        this.forcaJogoDomains.add(forcaJogoDomains);
    }
}
