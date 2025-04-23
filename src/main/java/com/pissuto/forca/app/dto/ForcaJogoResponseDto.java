package com.pissuto.forca.app.dto;

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

    private String gameId;
    private String palavraSecreta;
    private String palavraMascarada;
    private List<String> palpites;
    private int bodyPieces;
    private int maxErrors;
    private String status;

}
