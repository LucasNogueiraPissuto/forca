package com.pissuto.forca.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForcaJogadorDomain {

    @Id
    private String id;
    private String email;

    private List<ForcaJogoDomain> forcaJogoDomains = new ArrayList<>();

    public ForcaJogadorDomain(String emailUsuario, ForcaJogoDomain jogoDomain) {
        this.email = emailUsuario;
        this.forcaJogoDomains.add(jogoDomain);
    }
}
