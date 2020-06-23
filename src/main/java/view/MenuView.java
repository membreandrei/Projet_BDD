package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class MenuView extends ViewPanel {

    private final JLabel titre = new JLabel("Menu");
    private final ArrayList<JButton> allButtons = new ArrayList<>();

    /**
     * Construit le MenuView avec les boutons, leur style
     */
    public MenuView() {
        GridLayout g1 = new GridLayout(8, 1);
        this.setLayout(g1);
        this.setBackground(Color.decode("#303030"));
        this.allButtons.add(new JButton("Afficher tous les médias"));
        this.allButtons.add(new JButton("Média avec temps de parole des hommes 2x fois supérieur à celui des femmes"));
        this.allButtons.add(new JButton("TV avec pourcentage temps de parole homme supérieur à X"));
        this.allButtons.add(new JButton("Pourcentage temps de parole"));
        this.allButtons.add(new JButton("Moyenne temps de parole par média et par année"));
        this.allButtons.add(new JButton("Importer à partir d'un csv"));
        this.allButtons.add(new JButton("Quitter le programme"));
        this.titre.setForeground(Color.white);
        this.titre.setBorder(new CompoundBorder(this.titre.getBorder(), new EmptyBorder(0, 90, 0, 0)));
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

    /**
     * Ajoute un contr�leur aux boutons pr�sents
     *
     * @param controller
     */
    protected void addListeners(Controller controller) {
        for (JButton button : this.allButtons) {
            super.addListener(controller, button);
        }
    }
}
