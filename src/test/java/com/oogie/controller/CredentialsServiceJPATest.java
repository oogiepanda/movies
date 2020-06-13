package com.oogie.controller;

import com.oogie.BaseTest;
import com.oogie.model.CredentialsEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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


}