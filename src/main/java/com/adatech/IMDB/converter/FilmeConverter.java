package com.adatech.IMDB.converter;

import com.adatech.IMDB.model.Filme;
import com.adatech.IMDB.vo.FilmeOMDB;
import com.adatech.IMDB.vo.FilmeVO;
import org.springframework.stereotype.Component;

@Component
public class FilmeConverter {

    public FilmeVO converteParaFilmeVO(Filme filme) {
        if (filme == null) {
            throw new IllegalArgumentException("Filme não pode ser nulo");
        }
        FilmeVO filmeVO = new FilmeVO();
        filmeVO.setId(filme.getId());
        filmeVO.setTitle(filme.getTitle());
        filmeVO.setPlot(filme.getPlot());
        filmeVO.setActors(filme.getActors());
        filmeVO.setGenre(filme.getGenre());
        filmeVO.setYear(filme.getYear());
        filmeVO.setRuntime(filme.getRuntime());
        return filmeVO;
    }

    public Filme converteOMDBParaFilme(FilmeOMDB filmeOMDB) {
        if (filmeOMDB == null) {
            throw new IllegalArgumentException("FilmeOMDB não pode ser nulo");
        }
        Filme filme = new Filme();
        filme.setTitle(filmeOMDB.getTitle());
        filme.setPlot(filmeOMDB.getPlot());
        filme.setActors(filmeOMDB.getActors());
        filme.setGenre(filmeOMDB.getGenre());
        filme.setYear(filmeOMDB.getYear());
        filme.setRuntime(filmeOMDB.getRuntime());
        return filme;
    }
}