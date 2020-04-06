package view;

import model.ProgrammeurBean;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

//TODO: Peut-être factoriser tout ça ?

public class ProgrammeurView extends JPanel {

    private ArrayList<JLabel> allLabels = new ArrayList<JLabel>();
    private ArrayList<JTextField> allTextFields = new ArrayList<JTextField>();


    public ProgrammeurView() {
        this.initProgrammeurView(true);
    }

    public ProgrammeurView(ProgrammeurBean data) {
        this.initProgrammeurView(false);
        this.populateProgrammeurView(data);
    }

    private void initProgrammeurView(Boolean isEmpty) {

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

        for (int i = 0; i < this.allLabels.size(); i++) {
            this.allTextFields.add(new JTextField());
        }

        this.setBackground(Color.decode("#303030"));
        this.setLayout(new GridLayout(6, 4));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        for (JLabel label : allLabels) {
            Integer index = allLabels.indexOf(label);
            JTextField jtf = this.allTextFields.get(index);

            label.setBackground(Color.decode("#3a3a3a"));
            label.setForeground(Color.white);
            label.setBorder(new EmptyBorder(0, 25, 0, 0));

            if (!isEmpty) {
                jtf.setEditable(false);
                jtf.setFocusable(false);
            }
            jtf.setBackground(Color.decode("#424242"));
            jtf.setForeground(Color.WHITE);
            jtf.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), new EmptyBorder(5, 5, 5, 5)));
            this.add(label);
            this.add(jtf);
        }
    }

    private void populateProgrammeurView(ProgrammeurBean data) {
        this.allTextFields.get(0).setText(data.getId() + "");
        this.allTextFields.get(1).setText(data.getPseudo() + "");
        this.allTextFields.get(2).setText(data.getNom() + "");
        this.allTextFields.get(3).setText(data.getPrenom() + "");
        this.allTextFields.get(4).setText(data.getAnNaissance() + "");
        this.allTextFields.get(5).setText(data.getSalaire() + "");
        this.allTextFields.get(6).setText(data.getPrime() + "");
        this.allTextFields.get(7).setText(data.getAdresse() + "");
        this.allTextFields.get(8).setText(data.getResponsable() + "");
        this.allTextFields.get(9).setText(data.getHobby() + "");
    }

}
