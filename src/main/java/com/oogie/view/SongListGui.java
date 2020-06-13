package com.oogie.view;

import javax.swing.*;

public class SongListGui {
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
    private JTextArea resultTextArea;
    private JButton createButton;
    private JButton retrieveButton;
    private JButton updateButton;
    private JButton deleteButton;

    public SongListGui() {

    }

    public static void main(String[] args) {
        SongListGui songListGui = new SongListGui();
    }
}
