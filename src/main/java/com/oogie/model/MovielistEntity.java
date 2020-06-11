package com.oogie.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movielist", schema = "movies", catalog = "")
public class MovielistEntity {
    private int id;
    private String moviename;
    private String director;
    private Integer year;
    private String genre;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "moviename")
    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    @Basic
    @Column(name = "director")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Basic
    @Column(name = "year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovielistEntity that = (MovielistEntity) o;
        return id == that.id &&
                Objects.equals(moviename, that.moviename) &&
                Objects.equals(director, that.director) &&
                Objects.equals(year, that.year) &&
                Objects.equals(genre, that.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, moviename, director, year, genre);
    }
}
