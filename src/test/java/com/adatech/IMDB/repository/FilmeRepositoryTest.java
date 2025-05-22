package com.adatech.IMDB.repository;

import com.adatech.IMDB.model.Filme;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@DataJpaTest //Essa anotação faz com que o Spring Boot inicie o banco de dados e o JPA, e depois ele destrói o banco de dados após a execução dos testes.
class FilmeRepositoryTest {
    @Autowired
    FilmeRepository filmeRepository;
    Filme filme;

    @BeforeEach
    void setUp() {
        filme = new Filme();
        filmeRepository.save(new Filme("Mission: Impossible - The Final Reckoning", "Our lives are the sum of our choices. Tom Cruise is Ethan Hunt in Mission: Impossible - The Final Reckoning.", "Vanessa Kirby, Tom Cruise, Hayley Atwell", "Action, Adventure, Thriller", "2025", "169 min"));
        filmeRepository.save(new Filme("The Forge", "After graduating from high school without any plans for the future, Isaiah receives a push to start making better life decisions.", "Cameron Arnett, Priscilla C. Shirer, Aspen Kennedy", "Drama, Family", "2024", "124 min"));
        filmeRepository.save(new Filme("The Internship", "Two salesmen whose careers have been torpedoed by the digital age find their way into a coveted internship at Google, where they must compete with a group of young, tech-savvy geniuses for a shot at employment.", "Vince Vaughn, Owen Wilson, Rose Byrne", "Comedy", "2013", "119 min"));
        filmeRepository.save(new Filme("Jobs", "The story of Steve Jobs' ascension from college dropout into one of the most revered creative entrepreneurs of the 20th century.", "Ashton Kutcher, Dermot Mulroney, Josh Gad", "Biography, Drama", "2013", "128 min"));
        filmeRepository.save(new Filme("Gung Ho", "When a Japanese automobile company buys an American plant, the American liaison must mediate the clash of work attitudes between the foreign management and native labor.", "Michael Keaton, Gedde Watanabe, George Wendt", "Comedy, Drama", "1986", "111 min"));
        filmeRepository.save(new Filme("The Karate Kid Part II", "Daniel accompanies his mentor, Mr. Miyagi, to Miyagi's childhood home in Okinawa. Miyagi visits his dying father and confronts his old rival, while Daniel falls in love and inadvertently makes a new rival of his own.", "Pat Morita, Ralph Macchio, Pat E. Johnson", "Action, Family, Sport", "1986", "113 min"));
        filmeRepository.save(new Filme("Dirty Dancing", "Spending the summer at a Catskills resort with her family, Frances Baby Houseman falls in love with the camp's dance instructor, Johnny Castle.", "Patrick Swayze, Jennifer Grey, Jerry Orbach", "Drama, Music, Romance", "1987", "100 min"));

    }

    @Test
    void deveSalvarUmFilmeValidoComSucesso() {

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
    void deveLancarExcecaoQuandoConstraintForViolada() {
        //Cenário
        filme.setTitle(null);

        //Execução
        DataIntegrityViolationException exception = Assertions.assertThrows(
                DataIntegrityViolationException.class, () -> filmeRepository.save(filme));

        //Validação
        Assertions.assertNotNull(exception);
    }

    @Test
    void deveBuscarTodosOsFilmesComSucesso() {
        //Execução
        List<Filme> filmes = filmeRepository.findAll();

        //Validação
        Assertions.assertNotNull(filmes);
        Assertions.assertEquals(7, filmes.size());
    }
}