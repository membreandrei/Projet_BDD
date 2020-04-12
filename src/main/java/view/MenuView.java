package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MenuView extends ViewPanel {

    private JLabel titre = new JLabel("Menu");
    private ArrayList<JButton> allButtons = new ArrayList<JButton>();

    public MenuView() {
        GridLayout g1 = new GridLayout(8, 1);
        this.setLayout(g1);
        this.setBackground(Color.decode("#303030"));
        this.allButtons.add(new JButton("Afficher tous les programmeurs"));
        this.allButtons.add(new JButton("Afficher un programmeur"));
        this.allButtons.add(new JButton("Supprimer un programmeur"));
        this.allButtons.add(new JButton("Ajouter un programmeur"));
        this.allButtons.add(new JButton("Modifier le salaire"));
        this.allButtons.add(new JButton("Tout les Menus"));
        this.allButtons.add(new JButton("Quitter le programme"));
        this.titre.setForeground(Color.white);
        this.titre.setBorder(new CompoundBorder(this.titre.getBorder(), new EmptyBorder(0,90,0,0)));
        addComponent(titre);
        for (JButton button : this.allButtons) {
            button.setBackground(Color.decode("#3a3a3a"));
            button.setForeground(Color.white);
            button.setFocusPainted(false);
            addComponent(button);
        }
    }

    public ArrayList<JButton> getAllButtons() {
        return allButtons;
    }

    protected void addListeners(Controller controller) {
        for (JButton button : this.allButtons) {
            super.addListener(controller, button);
        }
    }
}
