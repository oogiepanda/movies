package com.oogie.controller;

import com.oogie.model.MovielistEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class MovieListServiceJPA extends BaseServiceJPA {
    public MovieListServiceJPA(EntityManager entityManager) {
        super(entityManager);
    }

    public int create(MovielistEntity me) {
        entityManager.getTransaction().begin();
        MovielistEntity movielistEntity = clone(me);
        entityManager.persist(movielistEntity);
        Query query = entityManager.createNativeQuery("select max(id) from movielist");
        int val = (int) query.getSingleResult();
        entityManager.getTransaction().commit();
        return val;
    }

    private MovielistEntity clone(MovielistEntity me) {
        MovielistEntity movielistEntity = new MovielistEntity();
        movielistEntity.setMoviename(me.getMoviename());
        movielistEntity.setDirector(me.getDirector());
        movielistEntity.setYear(me.getYear());
        movielistEntity.setGenre(me.getGenre());
        return movielistEntity;
    }

    public List<MovielistEntity> retrieve(MovielistEntity me) {
        if (isEmpty(me)) {
            return Collections.emptyList();
        }
        StringBuilder sb = new StringBuilder("select m from MovielistEntity m where 1 = 1");
        if (!isNullOrEmpty(me.getMoviename())) {
            sb.append(" and moviename = '").append(me.getMoviename()).append("'");
        }
        if (!isNullOrEmpty(me.getDirector())) {
            sb.append(" and director = '").append(me.getDirector()).append("'");
        }
        if (me.getYear() != null) {
            sb.append(" and year = ").append(me.getYear());
        }
        if (!isNullOrEmpty(me.getGenre())) {
            sb.append(" and genre = '").append(me.getGenre()).append("'");
        }
        System.out.println(sb);
        TypedQuery<MovielistEntity> query = entityManager.createQuery(sb.toString(), MovielistEntity.class);
        List<MovielistEntity> movielistEntities = query.getResultList();
        return movielistEntities;
    }

    private boolean isEmpty(MovielistEntity me) {
        return (isNullOrEmpty(me.getMoviename()) &&
                isNullOrEmpty(me.getDirector()) &&
                me.getYear() == null &&
                isNullOrEmpty(me.getGenre()));
    }

    public void update(MovielistEntity me, int id) {
        MovielistEntity movielistEntity = entityManager.find(MovielistEntity.class, id);
        entityManager.getTransaction().begin();
        if (!isNullOrEmpty(me.getMoviename())) {
            movielistEntity.setMoviename(me.getMoviename());
        }
        if (!isNullOrEmpty(me.getDirector())) {
            movielistEntity.setDirector(me.getDirector());
        }
        if (me.getYear() != null) {
            movielistEntity.setYear(me.getYear());
        }
        if (!isNullOrEmpty(me.getGenre())) {
            movielistEntity.setGenre(me.getGenre());
        }
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        MovielistEntity movielistEntity = entityManager.find(MovielistEntity.class, id);
        entityManager.remove(movielistEntity);
        entityManager.getTransaction().commit();
    }
}
