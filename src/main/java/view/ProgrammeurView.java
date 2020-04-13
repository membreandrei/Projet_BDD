package view;

import controller.Controller;
import model.ProgrammeurBean;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class ProgrammeurView extends ViewPanel {

    private ArrayList<JLabel> allLabels = new ArrayList<>();
    private HashMap<String, JTextField> allTextFields = new HashMap<>();

    /**
     * Construit le ProgrammeurView dans le cas d'un ajout d'un nouveau programmeur
     */
    public ProgrammeurView() {
        this.initProgrammeurView(true, false);
    }

    /**
     * Construit le ProgrammeurView quand des données sont présentes
     * C'est-à-dire, quand on affiche les détails d'un programmeur déjà existant
     * @param data
     * @param modify
     */
    public ProgrammeurView(ProgrammeurBean data, boolean modify) {
        this.initProgrammeurView(false, modify);
        this.populateProgrammeurView(data);
    }

    /**
     * Initialise le ProgrammeurView en fonction des paramètres reçus
     * @param isEmpty
     * @param modify
     */
    private void initProgrammeurView(Boolean isEmpty, boolean modify) {

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
     * Remplit le ProgrammeurView avec les données du programmeur ProgrammeurBean data choisi
     * @param data
     */
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

    /**
     * Ajoute un contrôleur au bouton d'ajout
     * @param controller
     * @param add
     */
    public void actionButtonAdd(Controller controller, JButton add) {
        addListener(controller, add);
        controller.setPv(this);
    }
}
