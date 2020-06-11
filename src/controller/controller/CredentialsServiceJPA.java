package controller;

import model.CredentialsEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class CredentialsServiceJPA extends BaseServiceJPA{
    public CredentialsServiceJPA(EntityManager entityManager) {
        super(entityManager);
    }

    public int create(CredentialsEntity ce) {

    }

    private CredentialsEntity clone(CredentialsEntity ce) {

    }

    public List<CredentialsEntity> retrieve(CredentialsEntity ce) {

    }

    public void update(CredentialsEntity ce, int id) {

    }

    private boolean isEmpty(CredentialsEntity ce) {

    }

    public void delete(CredentialsEntity ce) {

    }
}
