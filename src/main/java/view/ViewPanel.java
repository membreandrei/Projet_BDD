package view;

import javax.swing.*;
import java.awt.*;

public abstract class ViewPanel extends JPanel{
    protected void addComponent(JComponent component){
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(component);
    }
}
