package view;

import controller.Controller;
import model.Media;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class MediaView extends ViewPanel {

    private ArrayList<JLabel> allLabels = new ArrayList<>();
    private HashMap<String, JTextField> allTextFields = new HashMap<>();

    /**
     * Construit le MediaView dans le cas d'un ajout d'un nouveau programmeur
     */
    public MediaView() {
        this.initMediaView(true, false);
    }

    /**
     * Construit le MediaView quand des données sont présentes
     * C'est-�-dire, quand on affiche les d�tails d'un programmeur d�j� existant
     * @param data
     * @param modify
     */
    public MediaView(Media data, boolean modify) {
        this.initMediaView(false, modify);
        this.populateMediaView(data);
    }

    /**
     * Initialise le MediaView en fonction des paramètres reçus
     * @param isEmpty
     * @param modify
     */
    private void initMediaView(Boolean isEmpty, boolean modify) {

        if (!isEmpty) {
            this.setLayout(new GridLayout(6, 4));
            this.allLabels.add(new JLabel("ID: "));
        } else {
            this.setLayout(new GridLayout(5, 4));
        }

        this.allLabels.add(new JLabel("Type: "));
        this.allLabels.add(new JLabel("INA: "));
        this.allLabels.add(new JLabel("Nom: "));
        this.allLabels.add(new JLabel("Public: "));

        String key;

        for (JLabel allLabel : this.allLabels) {
            key = allLabel.getText().replace(": ", "").toLowerCase();
            this.allTextFields.put(key, new JTextField());
            this.allTextFields.get(key).setCaretColor(Color.white);
            if (modify) {
                this.allTextFields.get("id").setEditable(false);
                this.allTextFields.get("id").setFocusable(false);
            }
        }

        this.setBackground(Color.decode("#303030"));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        for (JLabel label : allLabels) {
            key = label.getText().replace(": ", "").toLowerCase();
            JTextField jtf = this.allTextFields.get(key);

            label.setBackground(Color.decode("#3a3a3a"));
            label.setForeground(Color.white);
            label.setBorder(new EmptyBorder(0, 25, 0, 0));

            if (!isEmpty && !modify) {
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

    /**
     * Remplit le MediaView avec les donn�es du programmeur ProgrammeurBean data choisi
     * @param data
     */
    private void populateMediaView(Media data) {
        this.allTextFields.get("id").setText(data.getId() + "");
        /*this.allTextFields.get("pseudo").setText(data.getPseudo() + "");
        this.allTextFields.get("nom").setText(data.getNom() + "");
        this.allTextFields.get("pr�nom").setText(data.getPrenom() + "");
        this.allTextFields.get("naissance").setText(data.getAnNaissance() + "");
        this.allTextFields.get("salaire").setText(data.getSalaire() + "");
        this.allTextFields.get("prime").setText(data.getPrime() + "");
        this.allTextFields.get("adresse").setText(data.getAdresse() + "");
        this.allTextFields.get("responsable").setText(data.getResponsable() + "");
        this.allTextFields.get("hobby").setText(data.getHobby() + "");*/
    }

    public HashMap<String, JTextField> getAllTextFields() {
        return this.allTextFields;
    }

    /**
     * Ajoute un contr�leur au bouton d'ajout
     * @param controller
     * @param add
     */
    public void actionButtonAdd(Controller controller, JButton add) {
        addListener(controller, add);
        controller.setPv(this);
    }
}