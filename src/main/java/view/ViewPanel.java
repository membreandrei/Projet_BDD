package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class ViewPanel extends JPanel {

    protected void addListener(Controller controller, JButton button) {
        button.addActionListener(controller);
    }

    protected void addComponent(JComponent component) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(component);
    }
}
