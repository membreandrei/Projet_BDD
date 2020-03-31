package view;

import model.ProgrammeurBean;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//TODO: Peut-être factoriser tout ça ?

public class ProgrammeurView extends JFrame {

    private ArrayList<JLabel> allLabels = new ArrayList<JLabel>();
    private ArrayList<JTextField> allTextFields = new ArrayList<JTextField>();

    public ProgrammeurView(ProgrammeurBean data) {
        this.allLabels.add(new JLabel("ID: "));
        this.allLabels.add(new JLabel("Pseudo: "));
        this.allLabels.add(new JLabel("Nom: "));
        this.allLabels.add(new JLabel("Prénom: "));
        this.allLabels.add(new JLabel("Naissance: "));
        this.allLabels.add(new JLabel("Salaire: "));
        this.allLabels.add(new JLabel("Prime: "));
        this.allLabels.add(new JLabel("Adresse: "));
        this.allLabels.add(new JLabel("Responsable: "));
        this.allLabels.add(new JLabel("Hobby: "));

        this.allTextFields.add(new JTextField(data.getId() + ""));
        this.allTextFields.add(new JTextField(data.getPseudo()));
        this.allTextFields.add(new JTextField(data.getNom()));
        this.allTextFields.add(new JTextField(data.getPrenom()));
        this.allTextFields.add(new JTextField(data.getAnNaissance() + ""));
        this.allTextFields.add(new JTextField(data.getSalaire() + ""));
        this.allTextFields.add(new JTextField(data.getPrime() + ""));
        this.allTextFields.add(new JTextField(data.getAdresse()));
        this.allTextFields.add(new JTextField(data.getResponsable()));
        this.allTextFields.add(new JTextField(data.getHobby()));

        this.setBackground(Color.decode("#303030"));
        this.setLayout(new GridLayout(6, 4));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        for (JLabel label : allLabels) {
            Integer index = allLabels.indexOf(label);
            label.setLabelFor(allTextFields.get(index));
            label.setBackground(Color.decode("#3a3a3a"));
            label.setForeground(Color.white);
            label.setBorder(new EmptyBorder(0, 25, 0, 0));
            allTextFields.get(index).setEditable(false);
            allTextFields.get(index).setFocusable(false);
            allTextFields.get(index).setBackground(Color.decode("#424242"));
            allTextFields.get(index).setForeground(Color.WHITE);
            allTextFields.get(index).setMargin(new Insets(5, 5, 5, 5));
            this.add(label);
            this.add(allTextFields.get(index));
        }
    }

}
