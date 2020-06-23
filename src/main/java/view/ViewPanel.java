package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Classe abstraite h�ritant de JPanel et ajoutant quelques m�thodes en plus
 */
public abstract class ViewPanel extends JPanel {

    /**
     * Ajout un ActionListener � un bouton
     * @param controller
     * @param button
     */
    protected void addListener(Controller controller, JButton button) {
        button.addActionListener(controller);
    }

    /**
     * Ajoute un MouseListener � une JTable
     * @param controller
     * @param table
     */
    protected void addListener(Controller controller, JTable table){
        table.addMouseListener(controller);
    }

    /**
     * Ajoute un composant centr� � un panel h�ritant de ViewPanel
     * @param component
     */
    protected void addComponent(JComponent component) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(component);
    }
}
