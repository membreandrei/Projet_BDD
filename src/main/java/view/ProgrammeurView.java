package view;

import controller.Controller;
import model.ProgrammeurBean;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

//TODO: Peut-être factoriser tout ça ?

public class ProgrammeurView extends ViewPanel {

    private ArrayList<JLabel> allLabels = new ArrayList<JLabel>();
    private HashMap<String, JTextField> allTextFields = new HashMap<>();


    public ProgrammeurView() {
        this.initProgrammeurView(true);
    }

    public ProgrammeurView(ProgrammeurBean data) {
        this.initProgrammeurView(false);
        this.populateProgrammeurView(data);
    }

    //TODO deplacer pseudo quand ajout prog
    private void initProgrammeurView(Boolean isEmpty) {

        if (!isEmpty) {
            this.setLayout(new GridLayout(6, 4));
            this.allLabels.add(new JLabel("ID: "));
        } else {
            this.setLayout(new GridLayout(5, 4));
        }

        this.allLabels.add(new JLabel("Pseudo: "));
        this.allLabels.add(new JLabel("Nom: "));
        this.allLabels.add(new JLabel("Prénom: "));
        this.allLabels.add(new JLabel("Naissance: "));
        this.allLabels.add(new JLabel("Salaire: "));
        this.allLabels.add(new JLabel("Prime: "));
        this.allLabels.add(new JLabel("Adresse: "));
        this.allLabels.add(new JLabel("Responsable: "));
        this.allLabels.add(new JLabel("Hobby: "));

        String key;

        for (int i = 0; i < this.allLabels.size(); i++) {
            key = this.allLabels.get(i).getText().replace(": ", "").toLowerCase();
            this.allTextFields.put(key, new JTextField());
            this.allTextFields.get(key).setCaretColor(Color.white);
        }

        this.setBackground(Color.decode("#303030"));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        for (JLabel label : allLabels) {
            key = label.getText().replace(": ", "").toLowerCase();
            JTextField jtf = this.allTextFields.get(key);

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
        this.allTextFields.get("id").setText(data.getId() + "");
        this.allTextFields.get("pseudo").setText(data.getPseudo() + "");
        this.allTextFields.get("nom").setText(data.getNom() + "");
        this.allTextFields.get("prénom").setText(data.getPrenom() + "");
        this.allTextFields.get("naissance").setText(data.getAnNaissance() + "");
        this.allTextFields.get("salaire").setText(data.getSalaire() + "");
        this.allTextFields.get("prime").setText(data.getPrime() + "");
        this.allTextFields.get("adresse").setText(data.getAdresse() + "");
        this.allTextFields.get("responsable").setText(data.getResponsable() + "");
        this.allTextFields.get("hobby").setText(data.getHobby() + "");
    }

    public HashMap<String, JTextField> getAllTextFields() {
        return this.allTextFields;
    }

    public void actionButtonAdd(Controller controller, JButton add) {
        addListener(controller, add);
        controller.setPv(this);
    }
}
