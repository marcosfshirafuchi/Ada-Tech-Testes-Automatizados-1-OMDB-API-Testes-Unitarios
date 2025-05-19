package com.adatech.IMDB.service;

import com.adatech.IMDB.converter.FilmeConverter;
import com.adatech.IMDB.exception.FilmeNaoEncontradoException;
import com.adatech.IMDB.exception.ListaVaziaException;
import com.adatech.IMDB.model.Filme;
import com.adatech.IMDB.repository.FilmeRepository;
import com.adatech.IMDB.vo.FilmeOMDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

class FilmeServiceTest {
    //TODO: Implementar testes unitários para FilmeService
    private FilmeService service;
    private FilmeRepository repository;
    private FilmeConverter converter;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(FilmeRepository.class);
        converter = Mockito.mock(FilmeConverter.class);
        service = new FilmeService(repository, converter);
        restTemplate = Mockito.mock(RestTemplate.class);
        // Injeta o mock do RestTemplate diretamente
        ReflectionTestUtils.setField(service, "restTemplate", restTemplate);
        // Injeta a URL fake da API para simular chamada
        ReflectionTestUtils.setField(service, "urlApiFilmes", "http://www.omdbapi.com");
    }

    @Test
    void deveRetornarUmFilmePorIdComSucesso() {
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
        Assertions.assertEquals(id, filmeObtido.getId());

        //Valida se o nome do filme é igual o nome do filme obtivo do banco de dados
        Assertions.assertEquals("The Forge", filmeObtido.getTitle());

    }

    @Test
    void deveLancarExcecaoQuandoFilmeNaoEncontrado() {
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
    void deveBuscarTodosOsFilmesComSucesso() {
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
        Mockito.when(repository.findAll()).thenReturn(List.of(filme1, filme2, filme3, filme4, filme5));

        //Execução
        List<Filme> filmesObtidos = service.getForAll();

        //Validação
        // Verifica se a lista não é nula
        Assertions.assertNotNull(filmesObtidos);

        //Verifica a quantidade de filmes na lista
        Assertions.assertEquals(5, filmesObtidos.size());

        //Verifica se o nome do filme é igual ao nome do filme obtido da lista
        Assertions.assertEquals("The Forge", filmesObtidos.get(0).getTitle());
        Assertions.assertEquals("Fireproof", filmesObtidos.get(1).getTitle());
        Assertions.assertEquals("Facing the Giants", filmesObtidos.get(2).getTitle());
        Assertions.assertEquals("War Room", filmesObtidos.get(3).getTitle());
        Assertions.assertEquals("Overcomer", filmesObtidos.get(4).getTitle());
    }

    @Test
    void deveLancarExcecaoQuandoListaDeFilmesEstaVazia() {
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

    @Test
    void deveRetornarInformacoesDoFilmeQuandoTituloValido() {
        //Cenario
        String titulo = "The Karate Kid Part II";
        FilmeOMDB filmeNaAPIDoOMDB = new FilmeOMDB();
        filmeNaAPIDoOMDB.setTitle(titulo);
        filmeNaAPIDoOMDB.setYear("1986");
        filmeNaAPIDoOMDB.setRuntime("113 min");
        filmeNaAPIDoOMDB.setResponse("True");

        String urlEsperada = "http://www.omdbapi.com?t=" + titulo + "&apikey=158d281a";

        // Define o comportamento esperado do mock do RestTemplate
        Mockito.when(restTemplate.getForObject(urlEsperada, FilmeOMDB.class)).thenReturn(filmeNaAPIDoOMDB);

        // Execução
        FilmeOMDB resultado = service.getInformacoesFilme(titulo);

        // Validação
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(titulo, resultado.getTitle());
        Assertions.assertEquals("1986", resultado.getYear());
        Assertions.assertEquals("113 min", resultado.getRuntime());
    }

    @Test
    void deveLancarFilmeNaoEncontradoExceptionQuandoOMDBRetornarFalse() {
        //Cenario
        String titulo = "FilmeInexistente";
        FilmeOMDB filmeInvalido = new FilmeOMDB();
        filmeInvalido.setResponse("False");

        String urlEsperada = "http://mockapi.com?t=FilmeInexistente&apikey=158d281a";
        Mockito.when(restTemplate.getForObject(urlEsperada, FilmeOMDB.class)).thenReturn(filmeInvalido);

        //Execução
        FilmeNaoEncontradoException exception = Assertions.assertThrows(FilmeNaoEncontradoException.class, () -> service.getInformacoesFilme(titulo));

        //Validação
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Filme não encontrado na base do OMDB.", exception.getMessage());

    }

    @Test
    void deveLancarIllegalArgumentExceptionQuandoTituloForNuloOuVazio() {
        //Cenario
        String titulo = null;
        String titulo2 = "";
        String titulo3 = " ";
        Mockito.when(restTemplate.getForObject(titulo, FilmeOMDB.class)).thenReturn(null);
        Mockito.when(restTemplate.getForObject(titulo2, FilmeOMDB.class)).thenReturn(null);
        Mockito.when(restTemplate.getForObject(titulo3, FilmeOMDB.class)).thenReturn(null);

        //Execução
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> service.getInformacoesFilme(titulo));
        IllegalArgumentException exception2 = Assertions.assertThrows(IllegalArgumentException.class, () -> service.getInformacoesFilme(titulo2));
        IllegalArgumentException exception3 = Assertions.assertThrows(IllegalArgumentException.class, () -> service.getInformacoesFilme(titulo3));

        //Validação

        Assertions.assertNotNull(exception);
        Assertions.assertNotNull(exception2);
        Assertions.assertNotNull(exception3);
        Assertions.assertEquals("O campo de busca está vazio! Preencha com um título de filme válido.", exception.getMessage());
        Assertions.assertEquals("O campo de busca está vazio! Preencha com um título de filme válido.", exception2.getMessage());
        Assertions.assertEquals("O campo de busca está vazio! Preencha com um título de filme válido.", exception3.getMessage());

    }

    @Test
    void deveLancarFilmeNaoEncontradoExceptionQuandoOMDBRetornarNull() {
        //Cenário
        String titulo = "Invalido";
        String urlEsperada = "http://mockapi.com?t=Invalido&apikey=158d281a";

        Mockito.when(restTemplate.getForObject(urlEsperada, FilmeOMDB.class)).thenReturn(null);

        //Execução
        FilmeNaoEncontradoException exception =  Assertions.assertThrows(FilmeNaoEncontradoException.class, () -> service.getInformacoesFilme(titulo));

        //Validação
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Filme não encontrado na base do OMDB.", exception.getMessage());

    }

    @DisplayName("Deve encontrar o filme no banco de dados e excluí-lo")
    @Test
    void deveEncontrarFilmeNoBancoDeDadosEExcluiLo() {
        // Cenário
        Long id = 1L;
        Filme filmeMock = new Filme();
        filmeMock.setId(id);
        filmeMock.setTitle("Exemplo de Filme");

        // Mock do repositório para retornar o filme
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(filmeMock));

        // Execução
        service.delete(id);

        // Verificações
        // Verifica se o método findById foi chamado uma vez com o ID correto
        Mockito.verify(repository, Mockito.times(1)).findById(id);

        // Verifica se o método delete foi chamado uma vez com o filme encontrado
        Mockito.verify(repository, Mockito.times(1)).delete(filmeMock);

        // Verifica a ordem das chamadas: findById deve vir antes de delete
        InOrder inOrder = Mockito.inOrder(repository);
        inOrder.verify(repository).findById(id);
        inOrder.verify(repository).delete(filmeMock);
    }

    @Test
    void deveLancarUmaExcecaoAoNaoEncontrarUmFilmeNoBancoDeDados(){
        //Cenario
        Long id = 1L;
        //Aqui você deixa explicito o que espera do Mockito, neste caso quando o método findById da classe repository deve retonar uma exceção de filme não encontrado pelo Id
        Mockito.when(repository.findById(id)).thenThrow(new FilmeNaoEncontradoException("Filme não encontrado com o ID: " + id));

        //Execução
        //Vai dar exceção quando chamar o método delete da classe service
        FilmeNaoEncontradoException exception = Assertions.assertThrows(FilmeNaoEncontradoException.class,() -> service.delete(id));

        //Verificação

        //Valida se a exception não é nula
        Assertions.assertNotNull(exception);
        //Valida a mensagem é igual quando o filme não é encontrado
        Assertions.assertEquals("Filme não encontrado com o ID: " + id, exception.getMessage());
    }
}