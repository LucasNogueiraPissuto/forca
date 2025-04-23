package com.pissuto.forca.app.services;

import com.pissuto.forca.app.dto.ForcaJogoResponseDto;
import com.pissuto.forca.app.dto.WordDto;
import com.pissuto.forca.app.repository.ConfigRepository;
import com.pissuto.forca.app.repository.ForcaJogoRepository;
import com.pissuto.forca.app.repository.WordRepository;
import com.pissuto.forca.app.to.PalpiteTo;
import com.pissuto.forca.app.to.WordTo;
import com.pissuto.forca.domain.ConfigJogosDomain.ConfigJogosDomain;
import com.pissuto.forca.domain.ConfigJogosDomain.NivelConfigDomain;
import com.pissuto.forca.domain.ForcaJogoDomain;
import com.pissuto.forca.domain.WordDomain;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ServiceForcaJogo {

    @Autowired
    private ForcaJogoRepository forcaJogoRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private ConfigRepository configRepository;

    private final Random random = new Random();

    public ForcaJogoResponseDto iniciarNovoJogo(String dificuldade) {
        WordTo palavraSelecionada = converterDomain(buscarPalavraAleatoria());
        String palavraMascarada = mascararPalavra(palavraSelecionada);
        List<String> palpites = new ArrayList<String>();


        //Refatorar e fazer um findByLevel para trazer as informações somente do level selecionado.
        ConfigJogosDomain config = configRepository.findAll().get(0);

        NivelConfigDomain configLevel = config.getLevels()
                .stream()
                .filter(l -> l.getLevelName().equalsIgnoreCase(dificuldade))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Dificuldade não encontrada: " + dificuldade));

        ForcaJogoDomain jogoDomain = new ForcaJogoDomain(
                palavraSelecionada.getPalavra(),
                palavraMascarada,
                palpites,
                configLevel.getMaxErrors(),
                configLevel.getBodyPieces(),
                "Em andamento..."
        );

        jogoDomain = forcaJogoRepository.save(jogoDomain);

        return converterJogoDto(jogoDomain);
    }

    public ForcaJogoResponseDto validarPalpite(String idJogo, PalpiteTo palpite) throws BussinesException {

        Optional<ForcaJogoDomain> jogoAtualOptional = forcaJogoRepository.findById(idJogo);

        if (jogoAtualOptional.isEmpty()) {
            throw new BussinesException("Sessão de jogo não existe");
        }

        ForcaJogoDomain jogoAtual = jogoAtualOptional.get();

        System.out.println("Palpite: " + palpite.getPalpite());

        String letraPalpite = palpite.getPalpite().toLowerCase();
        String palavraOriginal = jogoAtual.getPalavraSecreta().toLowerCase();
        String palavraMascarada = jogoAtual.getPalavraMascarada();
        List<String> palpites = jogoAtual.getPalpites();

        if (palpites.contains(letraPalpite)) {
            throw new BussinesException("Letra já foi utilizada.");
        }

        palpites.add(letraPalpite);

        StringBuilder novaMascarada = new StringBuilder(palavraMascarada);

        boolean letraEncontrada = false;
        for (int i = 0; i < palavraOriginal.length(); i++) {
            if (palavraOriginal.charAt(i) == letraPalpite.charAt(0)) {
                novaMascarada.setCharAt(i, letraPalpite.charAt(0));
                letraEncontrada = true;
            }
        }

        jogoAtual.setPalavraMascarada(novaMascarada.toString());
        jogoAtual.setPalpites(palpites);

        if (!letraEncontrada) {
            int maxErrors = jogoAtual.getMaxErrors();
            jogoAtual.setMaxErrors(maxErrors - 1);
        }
        
        if (!novaMascarada.toString().contains("_")) {
            jogoAtual.setStatus("Vitória");
        } else if (jogoAtual.getMaxErrors() < 0) {
            jogoAtual.setStatus("Derrota");
        }

        forcaJogoRepository.save(jogoAtual);

        if (jogoAtual.getStatus() == "Vitória" || jogoAtual.getStatus() == "Derrota") {
            throw new BussinesException("Jogo finalizado.");
        }

        return converterJogoDto(jogoAtual);
    }


    public WordTo converterDomain(WordDomain palavra) {
        return new WordTo(palavra.getPalavra(), palavra.getDicas());
    }

    public ForcaJogoResponseDto converterJogoDto(ForcaJogoDomain forcaJogoDomain) {
        return new ForcaJogoResponseDto(
                forcaJogoDomain.getGameId(),
                forcaJogoDomain.getPalavraSecreta(),
                forcaJogoDomain.getPalavraMascarada(),
                forcaJogoDomain.getPalpites(),
                forcaJogoDomain.getBodyPieces(),
                forcaJogoDomain.getMaxErrors(),
                forcaJogoDomain.getStatus());
    }

    public WordDomain buscarPalavraAleatoria() {
        List<WordDomain> todasPalavras = wordRepository.findAll();

        if (todasPalavras.isEmpty()) {
            throw new RuntimeException("Não há palavras cadastradas no banco de dados.");
        }

        int index = random.nextInt(todasPalavras.size());
        return todasPalavras.get(index);
    }

    public String mascararPalavra(WordTo palavra) {
        String palavraOriginal = palavra.getPalavra();
        return palavraOriginal.replaceAll(".", "_");
    }

}
