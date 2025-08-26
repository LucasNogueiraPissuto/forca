package com.pissuto.forca.app.dto;

import com.pissuto.forca.domain.ForcaJogoDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForcaJogoResponseDto {

    private int gameId;
    private String palavraSecreta;
    private String wordId;
    private String palavraMascarada;
    private List<String> dicas;
    private List<String> palpites;
    private int maxErrors;
    private String email;
    private String status;
    private String mensagem;
}
