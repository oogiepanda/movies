package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "credentials", schema = "movies", catalog = "")
public class CredentialsEntity {
    private int id;
    private String username;
    private String password;
    private int affiliation;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "affiliation")
    public int getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(int affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialsEntity that = (CredentialsEntity) o;
        return id == that.id &&
                affiliation == that.affiliation &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, affiliation);
    }
}
