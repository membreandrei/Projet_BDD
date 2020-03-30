package view;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.lang.reflect.Field;

public class MenuView extends ViewPanel {

    private JLabel titre = new JLabel("Menu");
    private JButton displayProgs = new JButton("Afficher tous les programmeurs");
    private JButton displayProg = new JButton("Afficher un programmeur");
    private JButton deleteProg = new JButton("Supprimer un programmeur");
    private JButton addProg = new JButton("Ajouter un programmeur");
    private JButton editSalary = new JButton("Modifier le salaire");
    private JButton exitApplication = new JButton("Quitter le programme");

    public MenuView() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        addComponent(titre);
        addComponent(displayProgs);
        addComponent(displayProg);
        addComponent(deleteProg);
        addComponent(addProg);
        addComponent(editSalary);
        addComponent(exitApplication);

    }

}
