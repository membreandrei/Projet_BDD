package view;

import model.ProgrammeurBean;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//TODO: Peut-être factoriser tout ça ?

public class ProgrammeurView extends JOptionPane {

    private ArrayList<JLabel> allLabels = new ArrayList<JLabel>();
    private ArrayList<JTextField> allTextFields = new ArrayList<JTextField>();

    public ProgrammeurView(ProgrammeurBean data){
        this.allLabels.add(new JLabel("ID: "));
        this.allLabels.add(new JLabel("Nom: "));
        this.allLabels.add(new JLabel("Prénom: "));
        this.allLabels.add(new JLabel("Naissance: "));
        this.allLabels.add(new JLabel("Pseudo: "));
        this.allLabels.add(new JLabel("Salaire: "));
        this.allLabels.add(new JLabel("Prime: "));
        this.allLabels.add(new JLabel("Adresse: "));
        this.allLabels.add(new JLabel("Responsable: "));
        this.allLabels.add(new JLabel("Hobby: "));

        this.allTextFields.add(new JTextField());
        this.allTextFields.add(new JTextField());
        this.allTextFields.add(new JTextField());
        this.allTextFields.add(new JTextField());
        this.allTextFields.add(new JTextField());
        this.allTextFields.add(new JTextField());
        this.allTextFields.add(new JTextField());
        this.allTextFields.add(new JTextField());
        this.allTextFields.add(new JTextField());
        this.allTextFields.add(new JTextField());

        this.setBackground(Color.decode("#303030"));

        for(JLabel label : allLabels){
            Integer index = allLabels.indexOf(label);
            label.setLabelFor(allTextFields.get(index));
            label.setBackground(Color.decode("#3a3a3a"));
            label.setForeground(Color.white);
            this.add(label);
            this.add(allTextFields.get(index));
        }


    }

}
