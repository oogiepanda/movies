package com.oogie.controller;

import com.oogie.BaseTest;
import com.oogie.model.CredentialsEntity;
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

class CredentialsServiceJPATest extends BaseTest {
    private static CredentialsServiceJPA credentialsServiceJPA;
    private static EntityManager entityManager;
    private static EntityManagerFactory emfactory;

    @BeforeAll
    public static void config() {
        emfactory = Persistence.createEntityManagerFactory("movies");
        entityManager = emfactory.createEntityManager();
        credentialsServiceJPA = new CredentialsServiceJPA(entityManager);
    }

    @AfterAll
    public static void destroy() {
        entityManager.close();
        emfactory.close();
    }

    private CredentialsEntity createCredentialsEntity() {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUsername("admin");
        credentialsEntity.setPassword("admin");
        credentialsEntity.setAffiliation(1);
        return credentialsEntity;
    }

    private CredentialsEntity createCredentialsEntity2() {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUsername("user");
        credentialsEntity.setPassword("user");
        credentialsEntity.setAffiliation(2);
        return credentialsEntity;
    }

    @Test
    void crudJPA() {
        CredentialsEntity orig = createCredentialsEntity();
        int id = credentialsServiceJPA.create(orig);
        List<CredentialsEntity> nuCred = credentialsServiceJPA.retrieve(orig);
        assertThat(orig.getUsername(), is(nuCred.get(0).getUsername()));

        nuCred.get(0).setAffiliation(2);
        credentialsServiceJPA.update(nuCred.get(0), id);
        List<CredentialsEntity> thirdCred = credentialsServiceJPA.retrieve(nuCred.get(0));
        assertThat(thirdCred.get(0).getAffiliation(), is(2));

        credentialsServiceJPA.delete(id);
        List<CredentialsEntity> fourthCred = credentialsServiceJPA.retrieve(orig);
        assertThat(fourthCred.size(), is(0));
    }


    @Test
    public void retrieveManyResults() {
        CredentialsEntity original = createCredentialsEntity();
        List<CredentialsEntity> nuCredentials = createManyResults(3);
        CredentialsEntity searchCred = new CredentialsEntity();
        searchCred.setUsername(original.getUsername());
        List<CredentialsEntity> credentials = credentialsServiceJPA.retrieve(searchCred);
        assertThat(credentials.size(), is(3));
        for (CredentialsEntity c : nuCredentials) {
            credentialsServiceJPA.delete(c.getId());
        }
    }

    private List<CredentialsEntity> createManyResults(int counter) {
        List<CredentialsEntity> credentials = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            CredentialsEntity nuCred = createCredentialsEntity();
            int id = credentialsServiceJPA.create(nuCred);
            CredentialsEntity clone = clone(nuCred, id);
            credentials.add(clone);
        }
        return credentials;
    }

    private CredentialsEntity clone(CredentialsEntity credentials, int id) {
        CredentialsEntity clone = new CredentialsEntity();
        clone.setId(id);
        clone.setUsername(credentials.getUsername());
        clone.setPassword(credentials.getPassword());
        clone.setAffiliation(credentials.getAffiliation());
        return clone;
    }
}