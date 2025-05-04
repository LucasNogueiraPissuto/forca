package com.pissuto.forca.app.services;

import com.pissuto.forca.app.dto.ForcaJogadorResponseDto;
import com.pissuto.forca.app.dto.ForcaJogoResponseDto;
import com.pissuto.forca.app.repository.ConfigRepository;
import com.pissuto.forca.app.repository.ForcaJogadorRepository;
import com.pissuto.forca.app.repository.WordRepository;
import com.pissuto.forca.app.to.PalpiteTo;
import com.pissuto.forca.app.to.WordTo;
import com.pissuto.forca.domain.ConfigJogosDomain.ConfigJogosDomain;
import com.pissuto.forca.domain.ConfigJogosDomain.NivelConfigDomain;
import com.pissuto.forca.domain.ForcaJogadorDomain;
import com.pissuto.forca.domain.ForcaJogoDomain;
import com.pissuto.forca.domain.WordDomain;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ServiceForcaJogo {

    @Autowired
    private ForcaJogadorRepository forcaJogadorRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private ConfigRepository configRepository;

    private final Random random = new Random();


    public ForcaJogoResponseDto iniciarNovoJogo(String dificuldade, String emailUsuario) throws BussinesException {
        WordTo palavraSelecionada = converterDomain(buscarPalavraAleatoria());
        String palavraMascarada = mascararPalavra(palavraSelecionada);
        List<String> palpites = new ArrayList<>();

        ConfigJogosDomain config = configRepository.findAll().get(0);
        NivelConfigDomain configLevel = config.getLevels()
                .stream()
                .filter(l -> l.getLevelName().equalsIgnoreCase(dificuldade))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Dificuldade não encontrada: " + dificuldade));

        Optional<ForcaJogadorDomain> jogadorExistente = forcaJogadorRepository.findByEmail(emailUsuario);
        ForcaJogadorDomain jogadorDomain;
        int idJogo;

        if (jogadorExistente.isPresent()) {
            jogadorDomain = jogadorExistente.get();
            idJogo = jogadorDomain.getForcaJogoDomains().size() + 1;
        } else {
            jogadorDomain = new ForcaJogadorDomain();
            jogadorDomain.setEmail(emailUsuario);
            idJogo = 1;
        }

        ForcaJogoDomain jogoDomain = new ForcaJogoDomain(
                idJogo,
                palavraSelecionada.getPalavra(),
                palavraMascarada,
                palpites,
                configLevel.getMaxErrors(),
                "Em andamento..."
        );

        jogadorDomain.getForcaJogoDomains().add(jogoDomain);
        forcaJogadorRepository.save(jogadorDomain);

        return converterJogoDto(jogoDomain, emailUsuario);
    }

    public ForcaJogoResponseDto validarPalpite(int idJogo, PalpiteTo palpite) throws BussinesException {

        ForcaJogadorDomain jogador = forcaJogadorRepository.findByEmail(palpite.getEmail())
                .orElseThrow(() -> new BussinesException("Jogador não existe"));

        ForcaJogoDomain jogoAtual = jogador.getForcaJogoDomains().stream()
                .filter(jogo -> jogo.getGameId() == idJogo)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));

        if ("Vitória".equals(jogoAtual.getStatus()) || "Derrota".equals(jogoAtual.getStatus())) {
            return converterJogoDto(jogoAtual, palpite.getEmail(), "Jogo já foi finalizado.");
        }

        String letraPalpite = palpite.getPalpite().toLowerCase();
        String palavraOriginal = jogoAtual.getPalavraSecreta().toLowerCase();
        String palavraMascarada = jogoAtual.getPalavraMascarada();
        List<String> palpites = jogoAtual.getPalpites();

        if (palpites.contains(letraPalpite)) {
            return converterJogoDto(jogoAtual, palpite.getEmail(), "Letra já foi utilizada.");
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
            jogoAtual.setMaxErrors(jogoAtual.getMaxErrors() - 1);
        }

        if (!novaMascarada.toString().contains("_")) {
            jogoAtual.setStatus("Vitória");
        } else if (jogoAtual.getMaxErrors() < 0) {
            jogoAtual.setStatus("Derrota");
        }

        forcaJogadorRepository.save(jogador);

        return converterJogoDto(jogoAtual, palpite.getEmail(), "Palpite registrado com sucesso.");
    }


    public WordTo converterDomain(WordDomain palavra) {
        return new WordTo(palavra.getPalavra(), palavra.getDicas());
    }

    public ForcaJogoResponseDto converterJogoDto(ForcaJogoDomain forcaJogoDomain, String email, String mensagem) {
        return new ForcaJogoResponseDto(
                forcaJogoDomain.getGameId(),
                forcaJogoDomain.getPalavraSecreta(),
                forcaJogoDomain.getPalavraMascarada(),
                forcaJogoDomain.getPalpites(),
                forcaJogoDomain.getMaxErrors(),
                email,
                forcaJogoDomain.getStatus(),
                mensagem
        );
    }

    public ForcaJogoResponseDto converterJogoDto(ForcaJogoDomain forcaJogoDomain, String email) {
        return converterJogoDto(forcaJogoDomain, email, null);
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
