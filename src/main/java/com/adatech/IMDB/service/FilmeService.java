package com.adatech.IMDB.service;

import com.adatech.IMDB.converter.FilmeConverter;
import com.adatech.IMDB.dto.FilmeDTO;
import com.adatech.IMDB.exception.FilmeNaoEncontradoException;
import com.adatech.IMDB.exception.ListaVaziaException;
import com.adatech.IMDB.model.Filme;
import com.adatech.IMDB.repository.FilmeRepository;
import com.adatech.IMDB.vo.FilmeOMDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final FilmeConverter filmeConverter;

    @Value("${url.apiFilmes}")
    private String urlApiFilmes;

    private RestTemplate restTemplate;

    private final String PARAM_API_KEY = "&apikey=158d281a";

    public FilmeService(FilmeRepository filmeRepository, FilmeConverter filmeConverter) {
        this.filmeRepository = filmeRepository;
        this.filmeConverter = filmeConverter;
    }

    public FilmeOMDB getInformacoesFilme(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo de busca está vazio! Preencha com um título de filme válido.");
        }

        RestTemplate restTemplate = new RestTemplate();
        FilmeOMDB filmeOMDB = restTemplate.getForObject(urlApiFilmes + "?t=" + titulo + PARAM_API_KEY, FilmeOMDB.class);

        if (filmeOMDB == null || "False".equalsIgnoreCase(filmeOMDB.getResponse())) {
            throw new FilmeNaoEncontradoException("Filme não encontrado na base do OMDB.");
        }

        return filmeOMDB;
    }

    public Filme save(FilmeDTO filmeDTO) {
        if (filmeDTO == null || filmeDTO.getTitle() == null || filmeDTO.getTitle().isBlank()) {
            throw new IllegalArgumentException("O título do filme não pode ser nulo ou vazio");
        }

        Filme filme = filmeConverter.converteOMDBParaFilme(getInformacoesFilme(filmeDTO.getTitle()));
        return filmeRepository.save(filme);
    }


    public Filme getById(Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        return filme.orElseThrow(() -> new FilmeNaoEncontradoException("Filme não encontrado com o ID: " + id));
    }

    public List<Filme> getForAll() {
        List<Filme> filmes = filmeRepository.findAll();
        if (filmes.isEmpty()) {
            throw new ListaVaziaException("Nenhum filme cadastrado na lista.");
        }
        return filmes;
    }

    public void delete(Long id) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new FilmeNaoEncontradoException("Filme não encontrado com o ID: " + id));
        filmeRepository.delete(filme);
    }

}
