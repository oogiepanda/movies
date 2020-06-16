package com.oogie.view;

import com.oogie.controller.MovieListServiceJPA;
import com.oogie.model.CredentialsEntity;
import com.oogie.model.MovielistEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Scanner;

public class MovieListGui {
    public JFrame frame;
    private JPanel panel;
    private JTextField idTextField;
    private JTextField movieNameTextField;
    private JTextField directorTextField;
    private JTextField yearTextField;
    private JTextField genreTextField;
    private JLabel idLabel;
    private JLabel movieNameLabel;
    private JLabel directorLabel;
    private JLabel yearLabel;
    private JLabel genreLabel;
    private JTextArea outputTextArea;
    private JButton createButton;
    private JButton retrieveButton;
    private JButton updateButton;
    private JButton deleteButton;

    private MovieListServiceJPA movieListServiceJPA;
    private List<CredentialsEntity> credentials;
    private int id = 0;

    public MovieListGui(final MainApp mainApp, final EntityManager entityManager, final List<CredentialsEntity> credentials) {
        this.credentials = credentials;
        movieListServiceJPA = new MovieListServiceJPA(entityManager);
        frame = new JFrame("Movie List");
        frame.setSize(300, 300);
        panel = new JPanel();
        idTextField = new JTextField(20);
        movieNameTextField = new JTextField(20);
        directorTextField = new JTextField(20);
        yearTextField = new JTextField(20);
        genreTextField = new JTextField(20);
        outputTextArea = new JTextArea(5, 25);
        outputTextArea.setEditable(false);
        frame.setContentPane(panel);
        if (isAdmin(credentials.get(0))) {
            panel.add(idLabel);
            panel.add(idTextField);
        }

        panel.add(movieNameLabel);
        panel.add(movieNameTextField);
        panel.add(directorLabel);
        panel.add(directorTextField);
        panel.add(yearLabel);
        panel.add(yearTextField);
        panel.add(genreLabel);
        panel.add(genreTextField);
        panel.add(createButton);
        panel.add(retrieveButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(outputTextArea);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MovielistEntity movielistEntity = createMovieListEntity();
                    id = movieListServiceJPA.create(movielistEntity);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MovielistEntity movielistEntity = createMovieListEntity();
                    List<MovielistEntity> movies = movieListServiceJPA.retrieve(movielistEntity);
                    StringBuilder sb = new StringBuilder();
                    for (MovielistEntity m : movies) {
                        sb.append("ID: ").append(m.getId()).append(", Movie Name: ").append(m.getMoviename()).append(", Director: ")
                                .append(m.getDirector()).append(", Year: ").append(m.getYear()).append(", Genre: ").append(m.getGenre())
                                .append("\n");
                    }
                    outputTextArea.setText(sb.toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MovielistEntity movielistEntity = createMovieListEntity();
                    movieListServiceJPA.update(movielistEntity, id);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!idTextField.getText().isEmpty()) {
                        Scanner s = new Scanner(idTextField.getText());
                        boolean check = true;
                        if (!s.hasNextInt()) {
                            check = false;
                        }
                        if (check == true) {
                            id = Integer.parseInt(idTextField.getText());
                            movieListServiceJPA.delete(id);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Error. Please input an integer.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error. Text field is empty.");
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        frame.addWindowListener(new java.awt.event.WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                updateButton.setVisible(false);
                deleteButton.setVisible(false);
                CredentialsEntity ce = credentials.get(0);
                if (isAdmin(ce)) {
                    updateButton.setVisible(true);
                    deleteButton.setVisible(true);
                } else if (isUser(ce)) {
                    updateButton.setVisible(true);
                } else {
                    createButton.setVisible(false);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                mainApp.destroy();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    private boolean isAdmin(CredentialsEntity ce) {
        return ce.getAffiliation() == 1;
    }

    private boolean isUser(CredentialsEntity ce) {
        return ce.getAffiliation() == 2;
    }

    private MovielistEntity createMovieListEntity() {
        MovielistEntity movielistEntity = new MovielistEntity();
        if (!movieNameTextField.getText().isEmpty()) {
            movielistEntity.setMoviename(movieNameTextField.getText());
        }
        if (!directorTextField.getText().isEmpty()) {
            movielistEntity.setDirector(directorTextField.getText());
        }
        if (!yearTextField.getText().isEmpty()) {
            Integer yearInt = Integer.parseInt(yearTextField.getText());
            movielistEntity.setYear(yearInt);
        }
        if (!genreTextField.getText().isEmpty()) {
            movielistEntity.setGenre(genreTextField.getText());
        }
        return movielistEntity;
    }
}
