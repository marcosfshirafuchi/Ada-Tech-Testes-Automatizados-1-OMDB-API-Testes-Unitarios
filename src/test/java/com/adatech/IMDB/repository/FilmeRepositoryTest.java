package com.adatech.IMDB.repository;

import com.adatech.IMDB.dto.FilmeDTO;
import com.adatech.IMDB.model.Filme;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@SpringBootTest
class FilmeRepositoryTest {
    @Autowired
    FilmeRepository filmeRepository;
    Filme filme;
    FilmeDTO filmeDTO;
    @BeforeEach
    void setUp(){
        filme = new Filme();
        filmeDTO = new FilmeDTO();
    }

    @Test
    void deveSalvarUmFilmeValidoComSucesso(){

        //Cenário
        filme.setTitle("Mission: Impossible - The Final Reckoning");
        filme.setYear("2025");
        filme.setPlot("Our lives are the sum of our choices. Tom Cruise is Ethan Hunt in Mission: Impossible - The Final Reckoning.");
        filme.setRuntime("169 min");
        filme.setActors("Vanessa Kirby, Tom Cruise, Hayley Atwell");
        filme.setGenre("Action, Adventure, Thriller");

        //Execução
        Filme filmeSalvo = filmeRepository.save(filme);

        //Validação
        Assertions.assertNotNull(filmeSalvo);
        Assertions.assertNotNull(filmeSalvo.getId());
        Optional<Filme> filmeOptional = filmeRepository.findById(filmeSalvo.getId());
        Assertions.assertTrue(filmeOptional.isPresent());
        Assertions.assertEquals(filmeSalvo.getTitle(), filmeOptional.get().getTitle());
        Assertions.assertEquals(filmeSalvo.getYear(), filmeOptional.get().getYear());
    }

    @Test
    void deveLancarExcecaoQuandoConstraintForViolada(){
        //Cenário
        filme.setTitle(null);

        //Execução
        DataIntegrityViolationException exception = Assertions.assertThrows(
                DataIntegrityViolationException.class, () -> filmeRepository.save(filme));

        //Validação
        Assertions.assertNotNull(exception);
    }
}