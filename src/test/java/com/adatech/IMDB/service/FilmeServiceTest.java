package com.adatech.IMDB.service;

import com.adatech.IMDB.converter.FilmeConverter;
import com.adatech.IMDB.model.Filme;
import com.adatech.IMDB.repository.FilmeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class FilmeServiceTest {
    //TODO: Implementar testes unitários para FilmeService
    private FilmeService service;
    private FilmeRepository repository;
    private FilmeConverter converter;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(FilmeRepository.class);
        converter = Mockito.mock(FilmeConverter.class);
        service = new FilmeService(repository,converter);
    }

    @Test
    void deveRetornarUmFilmePorIdComSucesso(){
        //Cenario
        Long id = 1L;

        //Cria um filme salvo no Banco De Dados
        Filme filmeQueEstaSalvoNoBancoDeDados = new Filme();
        filmeQueEstaSalvoNoBancoDeDados.setId(id);
        filmeQueEstaSalvoNoBancoDeDados.setTitle("The Forge");
        filmeQueEstaSalvoNoBancoDeDados.setPlot("After graduating from high school without any plans for the future, Isaiah receives a push to start making better life decisions.");
        filmeQueEstaSalvoNoBancoDeDados.setActors("Cameron Arnett, Priscilla C. Shirer, Aspen Kennedy");
        filmeQueEstaSalvoNoBancoDeDados.setGenre("Drama, Family");
        filmeQueEstaSalvoNoBancoDeDados.setYear("2024");
        filmeQueEstaSalvoNoBancoDeDados.setRuntime("124 min");

        //Mockito simula o comportamento da repository quando chamada e retorna um filme salvo no banco de dados
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(filmeQueEstaSalvoNoBancoDeDados));

        //Execução
        Filme filmeObtido = service.getById(id);


        //Validação

        //Valida se o filmeObtido não é nulo
        Assertions.assertNotNull(filmeObtido);

        //Valida se o Id é igual id do filmeObtido do banco de dados
        Assertions.assertEquals(id,filmeObtido.getId());

        //Valida se o nome do filme é igual o nome do filme obtivo do banco de dados
        Assertions.assertEquals("The Forge",filmeObtido.getTitle());

    }
}