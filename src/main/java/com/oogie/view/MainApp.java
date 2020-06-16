package com.oogie.view;

import com.oogie.model.CredentialsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
    private EntityManager entityManager;
    private EntityManagerFactory emfactory;
    List<CredentialsEntity> credentials = new ArrayList<>();

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        mainApp.run();
    }

    public void run() {
        config();
        final MainApp mainApp = this;
        FlowLayout flowLayout = new FlowLayout();
        final JFrame frame = new JFrame("User or Guest");
        frame.setLayout(flowLayout);
        frame.setSize(300, 100);
        JButton userButton = new JButton("User");
        JButton guestButton = new JButton("Guest");
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.add(userButton);
        frame.add(guestButton);
        frame.setVisible(true);
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CredentialsGui credentialsGui = new CredentialsGui(mainApp, entityManager);
                credentialsGui.frame.setVisible(true);
                credentialsGui.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.dispose();
            }
        });
        guestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CredentialsEntity guestCred = createGuestCred();
                credentials.add(guestCred);
                MovieListGui movieListGui = new MovieListGui(mainApp, entityManager, credentials);
                movieListGui.frame.setVisible(true);
                movieListGui.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.dispose();
            }
        });
    }

    private CredentialsEntity createGuestCred() {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setAffiliation(0);
        return credentialsEntity;
    }

    public void config() {
        emfactory = Persistence.createEntityManagerFactory("movies");
        entityManager = emfactory.createEntityManager();
    }

    public void destroy() {
        entityManager.close();
        emfactory.close();
    }
}
