package com.oogie.controller;

import com.oogie.model.CredentialsEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;


public class CredentialsServiceJPA extends BaseServiceJPA {
    public CredentialsServiceJPA(EntityManager entityManager) {
        super(entityManager);
    }

    public int create(CredentialsEntity ce) {
        entityManager.getTransaction().begin();
        CredentialsEntity credentialsEntity = clone(ce);
        entityManager.persist(credentialsEntity);
        Query query = entityManager.createNativeQuery("select max(id) from credentials");
        int val = (int) query.getSingleResult();
        entityManager.getTransaction().commit();
        return val;
    }

    private CredentialsEntity clone(CredentialsEntity ce) {
        CredentialsEntity clone = new CredentialsEntity();
        clone.setUsername(ce.getUsername());
        clone.setPassword(ce.getPassword());
        clone.setAffiliation(ce.getAffiliation());
        return clone;
    }

    public List<CredentialsEntity> retrieve(CredentialsEntity ce) {
        if (isEmpty(ce)) {
            return Collections.emptyList();
        }
        entityManager.getTransaction().begin();
        StringBuilder sb = new StringBuilder("select c from CredentialsEntity c where 1 = 1");
        if (!isNullOrEmpty(ce.getUsername())) {
            sb.append("and username = '").append(ce.getUsername()).append("'");
        }
        if (!isNullOrEmpty(ce.getPassword())) {
            sb.append("and password = '").append(ce.getUsername()).append("'");
        }
        if (ce.getAffiliation() != null) {
            sb.append("and affiliation = ").append(ce.getUsername());
        }
        System.out.println(sb);
        TypedQuery<CredentialsEntity> query = entityManager.createQuery(sb.toString(), CredentialsEntity.class);
        List<CredentialsEntity> credentialsEntities = query.getResultList();
        return credentialsEntities;
    }

    private boolean isEmpty(CredentialsEntity ce) {
        return (isNullOrEmpty(ce.getUsername()) &&
                isNullOrEmpty(ce.getPassword()) &&
                ce.getAffiliation() == null);
    }

    public void update(CredentialsEntity ce, int id) {
        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, id);
        entityManager.getTransaction().begin();
        if (!isNullOrEmpty(ce.getUsername())) {
            credentialsEntity.setUsername(ce.getUsername());
        }
        if (!isNullOrEmpty(ce.getPassword())) {
            credentialsEntity.setPassword(ce.getPassword());
        }
        if (ce.getAffiliation() != null) {
            credentialsEntity.setAffiliation(ce.getAffiliation());
        }
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, id);
        entityManager.remove(credentialsEntity);
        entityManager.getTransaction().commit();
    }
}
