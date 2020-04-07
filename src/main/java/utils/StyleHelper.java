package utils;

import javax.swing.*;
import java.awt.*;

public final class StyleHelper {

    private final static Color frameBackgroundColour = Color.decode("#303030");
    private final static Color componentBackgroundColour = Color.decode("#3a3a3a");
    private final static Color fgColour = Color.white;

    public static void setStyle(JComponent component){
        if(component.getClass().equals(JButton.class)){
            component.setBackground(componentBackgroundColour);
            component.setFocusable(false);
        } else{
            component.setBackground(frameBackgroundColour);
        }
        component.setForeground(fgColour);
    }

    public static void setStyle(JFrame frame){
        frame.setForeground(fgColour);
        frame.setBackground(frameBackgroundColour);
    }

}
