package com.adatech.IMDB.service;

import com.adatech.IMDB.converter.FilmeConverter;
import com.adatech.IMDB.exception.FilmeNaoEncontradoException;
import com.adatech.IMDB.exception.ListaVaziaException;
import com.adatech.IMDB.model.Filme;
import com.adatech.IMDB.repository.FilmeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
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

    @Test
    void deveLancarExcecaoQuandoFilmeNaoEncontrado(){
        //Cenario

        Long id = 2L;

        //Mockito simula o comportamento da repository quando chamada e não retorna um filme que não esta salvo no banco de dados
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //Execução
        FilmeNaoEncontradoException exception = Assertions.assertThrows(FilmeNaoEncontradoException.class, () -> service.getById(id));

        //Validação
        //Valida se a exception não é nula
        Assertions.assertNotNull(exception);

        //Valida a mensagem é igual quando o filme não é encontrado
        Assertions.assertEquals("Filme não encontrado com o ID: " + id, exception.getMessage());
    }

    @Test
    void deveBuscarTodosOsFilmesComSucesso(){
        //Cenario
        Filme filme1 = new Filme();
        filme1.setId(1L);
        filme1.setTitle("The Forge");
        filme1.setYear("2024");

        Filme filme2 = new Filme();
        filme2.setId(2L);
        filme2.setTitle("Fireproof");
        filme2.setYear("2008");

        Filme filme3 = new Filme();
        filme3.setId(3L);
        filme3.setTitle("Facing the Giants");
        filme3.setYear("2006");

        Filme filme4 = new Filme();
        filme4.setId(4L);
        filme4.setTitle("War Room");
        filme4.setYear("2015");

        Filme filme5 = new Filme();
        filme5.setId(5L);
        filme5.setTitle("Overcomer");
        filme5.setYear("2019");

        //Aqui você deixa explicito o que espera do Mockito, neste caso retornar uma lista filmes cadastrados
        Mockito.when(repository.findAll()).thenReturn(List.of(filme1,filme2,filme3,filme4,filme5));

        //Execução
        List<Filme> filmesObtidos = service.getForAll();

        //Validação
        // Verifica se a lista não é nula
        Assertions.assertNotNull(filmesObtidos);

        //Verifica a quantidade de filmes na lista
        Assertions.assertEquals(5,filmesObtidos.size());

        //Verifica se o nome do filme é igual ao nome do filme obtido da lista
        Assertions.assertEquals("The Forge",filmesObtidos.get(0).getTitle());
        Assertions.assertEquals("Fireproof",filmesObtidos.get(1).getTitle());
        Assertions.assertEquals("Facing the Giants",filmesObtidos.get(2).getTitle());
        Assertions.assertEquals("War Room",filmesObtidos.get(3).getTitle());
        Assertions.assertEquals("Overcomer",filmesObtidos.get(4).getTitle());
    }

    @Test
    void deveLancarExcecaoQuandoListaDeFilmesEstaVazia(){
        //Cenário
        //Aqui você deixa explicito o que espera do Mockito, neste caso retornar uma lista vazia
        Mockito.when(repository.findAll()).thenReturn(List.of());

        //Execução
        ListaVaziaException exception = Assertions.assertThrows(ListaVaziaException.class, () -> service.getForAll());

        //Validação
        Assertions.assertNotNull(exception);

        //Valida a mensagem é igual quando nenhum filme é cadastrado na lista
        Assertions.assertEquals("Nenhum filme cadastrado na lista.", exception.getMessage());

    }

}