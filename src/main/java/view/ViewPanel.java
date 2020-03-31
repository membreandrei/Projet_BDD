package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public abstract class ViewPanel extends JPanel {

    protected void addListener(Controller controller, JButton button) {
        button.addActionListener(controller);
    }
    protected void addListener(Controller controller, JTable table){
        table.addMouseListener((MouseListener) controller);
    }

    protected void addComponent(JComponent component) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(component);
    }
}
