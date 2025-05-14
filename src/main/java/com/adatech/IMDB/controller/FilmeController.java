package com.adatech.IMDB.controller;

import com.adatech.IMDB.converter.FilmeConverter;
import com.adatech.IMDB.dto.FilmeDTO;
import com.adatech.IMDB.model.Filme;
import com.adatech.IMDB.service.FilmeService;
import com.adatech.IMDB.vo.FilmeOMDB;
import com.adatech.IMDB.vo.FilmeVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/filme")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private FilmeConverter filmeConverter;


    @GetMapping
    @Operation(summary = "Busca um filme pela api do OMDB.",
            description = "Retorna um filme pela api do OMDB. Exemplo: The forge.")
    public ResponseEntity<FilmeOMDB> getFilme(@RequestParam("titulo") String titulo) {
        FilmeOMDB filmeOMDB = filmeService.getInformacoesFilme(titulo);
        return ResponseEntity.status(HttpStatus.OK).body(filmeOMDB);
    }

    @PostMapping
    @Operation(summary = "Salva um filme válido da api pelo título digitado",
            description = "Retorna com os dados do filme válido da api: title, plot, actors, genre, year e runtime")
    public ResponseEntity<FilmeVO> saveFilme(@RequestBody @Valid FilmeDTO filmeDTO) {
        FilmeVO filmeVO = filmeConverter.converteParaFilmeVO(
                filmeService.save(filmeDTO)
        );
        addHateoas(filmeVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(filmeVO);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Listar o filme cadastrado pelo Id.",
            description = "Retorna um filme cadastrado pelo Id.")
    public ResponseEntity<FilmeVO> getById(@PathVariable("id") Long id) {

        FilmeVO filmeVO = filmeConverter.converteParaFilmeVO(filmeService.getById(id));
        return ResponseEntity.ok(filmeVO);
    }

    @GetMapping("/listarFilmes")
    @Operation(summary = "Listar todos os filmes.",
            description = "Retorna uma lista de todos os filmes cadastrados.")
    public ResponseEntity<List<FilmeVO>> getAllFilmes() {
        List<Filme> filmes = filmeService.getForAll();

        List<FilmeVO> filmesVO = filmes.stream()
                .map(filme -> {
                    var filmeVO = filmeConverter.converteParaFilmeVO(filme);
                    addHateoas(filmeVO);
                    return filmeVO;
                })
                .toList();

        return ResponseEntity.ok(filmesVO);
    }


    private void addHateoas(FilmeVO filmeVO) {
        filmeVO.add(linkTo(methodOn(FilmeController.class).getById(filmeVO.getId()))
                .withSelfRel());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga um filme da lista por Id.",
            description = "Apaga um filme da lista pelo id solicitado.")
    public ResponseEntity<Void> deleteFilme(@PathVariable Long id) {
        filmeService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

}
