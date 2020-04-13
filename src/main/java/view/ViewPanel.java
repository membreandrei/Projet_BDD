package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Classe abstraite héritant de JPanel et ajoutant quelques méthodes en plus
 */
public abstract class ViewPanel extends JPanel {

    /**
     * Ajout un ActionListener à un bouton
     * @param controller
     * @param button
     */
    protected void addListener(Controller controller, JButton button) {
        button.addActionListener(controller);
    }

    /**
     * Ajoute un MouseListener à une JTable
     * @param controller
     * @param table
     */
    protected void addListener(Controller controller, JTable table){
        table.addMouseListener(controller);
    }

    /**
     * Ajoute un composant centré à un panel héritant de ViewPanel
     * @param component
     */
    protected void addComponent(JComponent component) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(component);
    }
}
