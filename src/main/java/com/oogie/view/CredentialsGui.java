package com.oogie.view;

import com.oogie.controller.CredentialsServiceJPA;
import com.oogie.model.CredentialsEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CredentialsGui {
    public JFrame frame;
    private JTextField usernameTextField;
    private JPasswordField passwordPField;
    private JButton confirmButton;
    private JLabel passwordLabel;
    private JLabel usernameLabel;
    private JPanel panel;

    private final MainApp mainApp;
    private final EntityManager entityManager;
    private CredentialsServiceJPA credentialsServiceJPA;
    public List<CredentialsEntity> credentials;

    public CredentialsGui(MainApp mainApp, EntityManager entityManager) {
        this.mainApp = mainApp;
        this.entityManager = entityManager;
        credentialsServiceJPA = new CredentialsServiceJPA(entityManager);
        frame = new JFrame();
        frame.setTitle("Enter Your Credentials");
        frame.setSize(275, 180);
        panel = new JPanel();
        usernameTextField = new JTextField(20);
        passwordPField = new JPasswordField(20);
        frame.setContentPane(panel);
        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(passwordLabel);
        panel.add(passwordPField);
        panel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!usernameTextField.getText().isEmpty() &&
                        passwordPField.getPassword().length > 0) {
                    if (usernameTextField.getText().length() <= 6 && passwordPField.getPassword().length <= 6) {
                        try {
                            CredentialsEntity credentialsEntity = createCredentialsEntity();
                            credentials = credentialsServiceJPA.retrieve(credentialsEntity);
                            if (!credentials.isEmpty()) {
                                openMoviesWindow();
                            } else {
                                JOptionPane.showMessageDialog(frame, "Error. Invalid credentials.");
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error. Username/Password must be no greater than 6 characters.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Error. Please enter your username and password.");
                }

            }
        });
    }

    public CredentialsEntity createCredentialsEntity() {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUsername(usernameTextField.getText());
        char[] passString = passwordPField.getPassword();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < passwordPField.getPassword().length; i++) {
            sb.append(passString[i]);
        }
        credentialsEntity.setPassword(sb.toString());
        return credentialsEntity;
    }

    public void openMoviesWindow() {
        MovieListGui movieListGui = new MovieListGui(mainApp, entityManager, credentials);
        movieListGui.frame.setVisible(true);
        movieListGui.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.dispose();
    }
}
