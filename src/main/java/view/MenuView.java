package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MenuView extends ViewPanel {

    private JLabel titre = new JLabel("Menu");
    private ArrayList<JButton> allButtons = new ArrayList<JButton>();

    public MenuView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        this.allButtons.add(new JButton("Afficher tous les programmeurs"));
        this.allButtons.add(new JButton("Afficher un programmeur"));
        this.allButtons.add(new JButton("Supprimer un programmeur"));
        this.allButtons.add(new JButton("Ajouter un programmeur"));
        this.allButtons.add(new JButton("Modifier le salaire"));
        this.allButtons.add(new JButton("Quitter le programme"));
        addComponent(titre);

        for (JButton button : this.allButtons) {
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
