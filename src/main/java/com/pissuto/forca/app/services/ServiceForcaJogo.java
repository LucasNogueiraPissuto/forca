package com.pissuto.forca.app.services;

import com.pissuto.forca.app.dto.ForcaJogoResponseDto;
import com.pissuto.forca.app.repository.ConfigRepository;
import com.pissuto.forca.app.repository.ForcaJogadorRepository;
import com.pissuto.forca.app.repository.WordRepository;
import com.pissuto.forca.app.to.PalpiteTo;
import com.pissuto.forca.app.to.WordTo;
import com.pissuto.forca.domain.ConfigJogosDomain.NivelConfigDomain;
import com.pissuto.forca.domain.ForcaJogadorDomain;
import com.pissuto.forca.domain.ForcaJogoDomain;
import com.pissuto.forca.domain.WordDomain;
import com.pissuto.forca.infra.exceptions.BussinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ServiceForcaJogo {

    @Autowired private ForcaJogadorRepository jogadorRepository;
    @Autowired private WordRepository palavraRepository;
    @Autowired private ConfigRepository configRepository;

    private final Random random = new Random();

    ForcaJogadorDomain jogadorAnonimo = new ForcaJogadorDomain("", new ArrayList<>());

    List<String> palavrasAdivinhadas = new ArrayList<>();

    public ForcaJogoResponseDto iniciarNovoJogo(String dificuldade, String email) throws BussinesException {
        NivelConfigDomain nivel = buscarNivelConfig(dificuldade);
        WordDomain palavraDomain = buscarPalavraAleatoria(email);
        WordTo palavraTo = converterDomain(palavraDomain);
        String palavraMascarada = mascararPalavra(palavraTo);

        ForcaJogadorDomain jogador = buscarOuCriarJogador(email);

        ForcaJogoDomain novoJogo = new ForcaJogoDomain(
                jogador.getForcaJogoDomains().size() + 1,
                palavraTo.getPalavra(),
                palavraDomain.getId(),
                palavraMascarada,
                new ArrayList<>(),
                0,
                new ArrayList<>(),
                nivel.getMaxErrors(),
                "Em andamento..."
        );

        jogador.getForcaJogoDomains().add(novoJogo);
        if (email != null && !email.isBlank() ){
            jogadorRepository.save(jogador);
        }

        return converterJogoDto(novoJogo, email);
    }

    public ForcaJogoResponseDto validarPalpite(int idJogo, PalpiteTo palpite) throws BussinesException {
        ForcaJogadorDomain jogador = buscarJogador(palpite.getEmail());
        ForcaJogoDomain jogo = buscarJogo(jogador, idJogo);

        if (jogoFinalizado(jogo)) return converterJogoDto(jogo, palpite.getEmail(), "Jogo já foi finalizado.");

        String letra = palpite.getPalpite().toLowerCase();
        if (jogo.getPalpites().contains(letra)) {
            return converterJogoDto(jogo, palpite.getEmail(), "Letra já foi utilizada.");
        }

        processarPalpite(jogo, letra);
        jogadorRepository.save(jogador);

        return converterJogoDto(jogo, palpite.getEmail(), "Palpite registrado com sucesso.");
    }

    public ForcaJogoResponseDto tentarAdivinharPalavra(int id, PalpiteTo tentativa) throws BussinesException {
        ForcaJogadorDomain jogador = buscarJogador(tentativa.getEmail());
        ForcaJogoDomain jogo = buscarJogo(jogador, id);

        if (jogoFinalizado(jogo)) throw new BussinesException("Esse jogo já foi finalizado.");

        if (jogo.getPalpites().size() > 3) {
            throw new BussinesException("O chute da palavra só é permitido nos três primeiros palpites.");
        }

        String chute = tentativa.getPalpite();
        String mensagem;

        if (chute.equalsIgnoreCase(jogo.getPalavraSecreta())) {
            jogo.setPalavraMascarada(jogo.getPalavraSecreta());
            jogo.setStatus("Vitória!");
            mensagem = "Palavra certa! Você venceu!";
        } else {
            jogo.setMaxErrors(0);
            jogo.setStatus("Derrota");
            mensagem = "Palavra errada! Você perdeu!";
        }

        jogadorRepository.save(jogador);
        return converterJogoDto(jogo, tentativa.getEmail(), mensagem);
    }

    public ForcaJogoResponseDto fornecerDica(int id, String email) throws BussinesException {
        ForcaJogadorDomain jogador = buscarJogador(email);
        ForcaJogoDomain jogo = buscarJogo(jogador, id);

        if (jogoFinalizado(jogo)) throw new BussinesException("Esse jogo já foi finalizado.");

        WordDomain palavra = palavraRepository.findById(jogo.getWordId())
                .orElseThrow(() -> new BussinesException("Palavra associada ao jogo não encontrada."));

        List<String> dicasDisponiveis = palavra.getDicas();
        int index = jogo.getDicasUsadas();

        if (index >= dicasDisponiveis.size()) {
            throw new BussinesException("Todas as dicas já foram utilizadas.");
        }

        String novaDica = dicasDisponiveis.get(index);
        jogo.getDicas().add(novaDica);
        jogo.setDicasUsadas(index + 1);
        jogo.setMaxErrors(jogo.getMaxErrors() - 1);

        jogadorRepository.save(jogador);
        return converterJogoDto(jogo, email, "Dica fornecida!");
    }

    // ----------------------- Métodos auxiliares -----------------------

    private NivelConfigDomain buscarNivelConfig(String dificuldade) throws BussinesException {
        return configRepository.findAll().get(0).getLevels().stream()
                .filter(l -> l.getLevelName().equalsIgnoreCase(dificuldade))
                .findFirst()
                .orElseThrow(() -> new BussinesException("Dificuldade não encontrada: " + dificuldade));
    }

    private ForcaJogadorDomain buscarOuCriarJogador(String email) {
        return (email == null || email.isBlank())
                ? jogadorAnonimo
                : jogadorRepository.findByEmail(email).orElseGet(() -> new ForcaJogadorDomain(email, new ArrayList<>()));
    }

    private ForcaJogadorDomain buscarJogador(String email) throws BussinesException {
        return (email == null || email.isBlank())
                ? jogadorAnonimo
                : jogadorRepository.findByEmail(email).orElseThrow(() -> new BussinesException("Jogador não encontrado"));
    }

    private ForcaJogoDomain buscarJogo(ForcaJogadorDomain jogador, int id) throws BussinesException {
        return jogador.getForcaJogoDomains().stream()
                .filter(j -> j.getGameId() == id)
                .findFirst()
                .orElseThrow(() -> new BussinesException("Jogo não encontrado"));
    }

    private boolean jogoFinalizado(ForcaJogoDomain jogo) {
        return !"Em andamento...".equalsIgnoreCase(jogo.getStatus());
    }

    private void processarPalpite(ForcaJogoDomain jogo, String letra) {
        String palavra = jogo.getPalavraSecreta().toLowerCase();
        StringBuilder mascarada = new StringBuilder(jogo.getPalavraMascarada());

        boolean acertou = false;
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) == letra.charAt(0)) {
                mascarada.setCharAt(i, letra.charAt(0));
                acertou = true;
            }
        }

        jogo.getPalpites().add(letra);
        jogo.setPalavraMascarada(mascarada.toString());

        if (!acertou) {
            jogo.setMaxErrors(jogo.getMaxErrors() - 1);
        }

        if (!mascarada.toString().contains("_")) {
            jogo.setStatus("Vitória");
        } else if (jogo.getMaxErrors() < 0) {
            jogo.setStatus("Derrota");
        }
    }

    public ForcaJogoResponseDto converterJogoDto(ForcaJogoDomain jogo, String email, String mensagem) {
        return new ForcaJogoResponseDto(
                jogo.getGameId(),
                jogo.getPalavraSecreta(),
                jogo.getWordId(),
                jogo.getPalavraMascarada(),
                jogo.getDicas(),
                jogo.getPalpites(),
                jogo.getMaxErrors(),
                email,
                jogo.getStatus(),
                mensagem
        );
    }

    public ForcaJogoResponseDto converterJogoDto(ForcaJogoDomain jogo, String email) {
        return converterJogoDto(jogo, email, null);
    }

    public WordTo converterDomain(WordDomain palavra) {
        return new WordTo(palavra.getPalavra(), palavra.getDicas());
    }

    public WordDomain buscarPalavraAleatoria(String email) throws BussinesException {
        List<WordDomain> palavras = palavraRepository.findAll();

        ForcaJogadorDomain jogador = buscarOuCriarJogador(email);
        List<ForcaJogoDomain> jogos = jogador.getForcaJogoDomains();

        for (ForcaJogoDomain jogo : jogos) {
            if (jogo.getStatus().equals("Vitória!")) {
                palavrasAdivinhadas.add(jogo.getPalavraSecreta());
            }
        }

        palavras.removeIf(p -> palavrasAdivinhadas.contains(p.getPalavra()));

        if (palavras.isEmpty()) {
            throw new RuntimeException("Não há palavras disponiveis");
        }

        return palavras.get(random.nextInt(palavras.size()));
    }

    public String mascararPalavra(WordTo palavra) {
        return palavra.getPalavra().replaceAll(".", "_");
    }
}

