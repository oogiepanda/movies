package com.oogie.model;

import javax.persistence.*;

@Entity
@Table(name = "movielist", schema = "movies", catalog = "")
public class MovielistEntity {
    private int id;
    private String moviename;
    private String director;
    private Integer year;
    private String genre;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "moviename", nullable = false, length = 35)
    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    @Basic
    @Column(name = "director", nullable = true, length = 20)
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Basic
    @Column(name = "year", nullable = true)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "genre", nullable = true, length = 10)
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

        if (id != that.id) return false;
        if (moviename != null ? !moviename.equals(that.moviename) : that.moviename != null) return false;
        if (director != null ? !director.equals(that.director) : that.director != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (moviename != null ? moviename.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }
}
