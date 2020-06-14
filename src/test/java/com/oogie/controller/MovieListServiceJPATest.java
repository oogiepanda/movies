package com.oogie.controller;

import com.oogie.BaseTest;
import com.oogie.model.MovielistEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MovieListServiceJPATest extends BaseTest {
    private static MovieListServiceJPA movieListServiceJPA;
    private static EntityManager entityManager;
    private static EntityManagerFactory emfactory;

    @BeforeAll
    public static void config() {
        emfactory = Persistence.createEntityManagerFactory("movies");
        entityManager = emfactory.createEntityManager();
        movieListServiceJPA = new MovieListServiceJPA(entityManager);
    }

    @AfterAll
    public static void destroy() {
        entityManager.close();
        emfactory.close();
    }

    private MovielistEntity createMovieListEntity() {
        MovielistEntity movielistEntity = new MovielistEntity();
        movielistEntity.setMoviename("Spiderman");
        movielistEntity.setDirector("Sam Raimi");
        movielistEntity.setYear(2002);
        movielistEntity.setGenre("Action");
        return movielistEntity;
    }

    @Test
    void crudJPA() {
        MovielistEntity orig = createMovieListEntity();
        int id = movieListServiceJPA.create(orig);
        List<MovielistEntity> nuMovies = movieListServiceJPA.retrieve(orig);
        assertThat(orig.getMoviename(), is(nuMovies.get(0).getMoviename()));
        assertThat(nuMovies.size(), is(1));

        nuMovies.get(0).setYear(2020);
        movieListServiceJPA.update(nuMovies.get(0),id);
        List<MovielistEntity> thirdMovies = movieListServiceJPA.retrieve(nuMovies.get(0));
        assertThat(thirdMovies.get(0).getYear(), is(2020));

        movieListServiceJPA.delete(id);
        List<MovielistEntity> fourthMovies = movieListServiceJPA.retrieve(orig);
        assertThat(fourthMovies.size(), is(0));
    }

    @Test
    void returnNoResults() {
        MovielistEntity searchMovie = new MovielistEntity();
        List<MovielistEntity> movies = movieListServiceJPA.retrieve(searchMovie);
        assertThat(movies.isEmpty(), is(true));
    }

    @Test
    void retrieveManyResults() {
        MovielistEntity orig = createMovieListEntity();
        List<MovielistEntity> movies = createManyResults(3);
        MovielistEntity search = new MovielistEntity();
        search.setYear(orig.getYear());
        List<MovielistEntity> nuMovies = movieListServiceJPA.retrieve(search);
        assertThat(nuMovies.size(), is(3));
        for (MovielistEntity m : movies) {
            movieListServiceJPA.delete(m.getId());
        }
    }

    private List<MovielistEntity> createManyResults(int counter) {
        List<MovielistEntity> movies = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            MovielistEntity nuMovie = createMovieListEntity();
            int id = movieListServiceJPA.create(nuMovie);
            MovielistEntity clone = clone(nuMovie, id);
            movies.add(clone);
        }
        return movies;
    }

    private MovielistEntity clone(MovielistEntity movielistEntity, int id) {
        MovielistEntity clone = new MovielistEntity();
        clone.setId(id);
        clone.setMoviename(movielistEntity.getMoviename());
        clone.setDirector(movielistEntity.getDirector());
        clone.setYear(movielistEntity.getYear());
        clone.setGenre(movielistEntity.getGenre());
        return clone;
    }
}