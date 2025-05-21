package com.adatech.IMDB.controller;

import com.adatech.IMDB.converter.FilmeConverter;
import com.adatech.IMDB.dto.FilmeDTO;
import com.adatech.IMDB.model.Filme;
import com.adatech.IMDB.service.FilmeService;
import com.adatech.IMDB.vo.FilmeOMDB;
import com.adatech.IMDB.vo.FilmeVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FilmeControllerTest {
    @InjectMocks
    private FilmeController controller;

    @Mock
    private FilmeService service;

    @Mock
    private FilmeConverter converter;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void deveBuscarTodosOsFilmesComSucesso() throws Exception {
        //Cenário
        Filme filme1 = new Filme();
        filme1.setId(1L);
        filme1.setTitle("Mission: Impossible - The Final Reckoning");
        filme1.setYear("2025");

        Filme filme2 = new Filme();
        filme2.setId(2L);
        filme2.setTitle("Rush Hour 3");
        filme2.setYear("2007");

        List<Filme> filmesRetornados = List.of(filme1, filme2);
        FilmeVO filmeVO1 = new FilmeVO();
        filmeVO1.setId(filme1.getId());
        filmeVO1.setTitle(filme1.getTitle());
        filmeVO1.setYear(filme1.getYear());

        FilmeVO filmeVO2 = new FilmeVO();
        filmeVO2.setId(filme2.getId());
        filmeVO2.setTitle(filme2.getTitle());
        filmeVO2.setYear(filme2.getYear());

        List<FilmeVO> filmesVO = List.of(filmeVO1, filmeVO2);

        Mockito.when(service.getForAll()).thenReturn(filmesRetornados);
        Mockito.when(converter.converteParaFilmeVO(filme1)).thenReturn(filmeVO1);
        Mockito.when(converter.converteParaFilmeVO(filme2)).thenReturn(filmeVO2);

        // Execução e Validação
        mockMvc.perform(MockMvcRequestBuilders.get("/filme/listarFilmes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(filmesVO)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deveCadastrarUmFilmeValidoComSucessoNoBancoDeDados() throws Exception {
        // CENÁRIO
        FilmeDTO request = new FilmeDTO();
        request.setTitle("Mission: Impossible - The Final Reckoning");

        Filme filme = new Filme();
        filme.setId(1L);
        filme.setTitle("Mission: Impossible - The Final Reckoning");
        filme.setYear("2025");

        FilmeVO filmeCriado = new FilmeVO();
        filmeCriado.setId(1L);
        filmeCriado.setTitle("Mission: Impossible - The Final Reckoning");
        filmeCriado.setYear("2025");
        filmeCriado.add(Link.of("/filme/1")); // HATEOAS fictício

        // MOCKS
        Mockito.when(service.save(Mockito.any())).thenReturn(filme);
        Mockito.when(converter.converteParaFilmeVO(Mockito.any())).thenReturn(filmeCriado);

        // EXECUÇÃO & VALIDAÇÃO
        mockMvc.perform(MockMvcRequestBuilders.post("/filme")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(filmeCriado)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deveRetornarUmFilmeValidoDaApiDoOMDBComSucesso() throws Exception {
        // CENÁRIO
        String titulo = "Mission: Impossible - The Final Reckoning";
        FilmeOMDB filmeValidoDaAPIDoOMB = new FilmeOMDB();
        filmeValidoDaAPIDoOMB.setTitle(titulo);
        filmeValidoDaAPIDoOMB.setYear("2025");
        Mockito.when(service.getInformacoesFilme(titulo)).thenReturn(filmeValidoDaAPIDoOMB);

        // Execução e Validação
        mockMvc.perform(MockMvcRequestBuilders.get("/filme")
                        .param("titulo", titulo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(filmeValidoDaAPIDoOMB)))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void deveBuscarUmFilmeCadastradoNoBancoDeDadosPeloIdComSucesso() throws Exception {
        //Cenário
        Long id = 1L;

        //Criação de filme para ser retornado pelo id válido
        Filme filme = new Filme();
        filme.setId(id);
        filme.setTitle("Mission: Impossible - The Final Reckoning");
        filme.setYear("2025");

        // Define o comportamento esperado do mock, quando envia um id válido para o método getById da service, ele retorna o filme criado acima
        Mockito.when(service.getById(id)).thenReturn(filme);

        //Criação de um filme cadastrado para ser retornado pelo id válido
        FilmeVO retornaFilmeCadastradoPeloId = new FilmeVO();
        retornaFilmeCadastradoPeloId.setId(filme.getId());
        retornaFilmeCadastradoPeloId.setTitle(filme.getTitle());

        // Define o comportamento esperado do mock, quando envia o filme da classe Filme para o método converteParaFilmeVO e retornar um filme já cadastrado no banco de dados
        Mockito.when(converter.converteParaFilmeVO(filme)).thenReturn(retornaFilmeCadastradoPeloId);

        //Execução E Validação
        mockMvc.perform(MockMvcRequestBuilders.get("/filme/{id}", id)
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(retornaFilmeCadastradoPeloId)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deveApagarUmFilmeCadastradoDoBancoDeDadosPorIdComSucesso() throws Exception {
        //Cenário
        Long id = 1L;

        //Execução e Validação
        mockMvc.perform(MockMvcRequestBuilders.delete("/filme/{id}", id)
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    private String asJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível mapear o objeto para json");
        }
    }
}