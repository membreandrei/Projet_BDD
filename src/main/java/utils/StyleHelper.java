package utils;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * Cette classe est dédiée au style général de l'application
 */
public final class StyleHelper {

    private final static Color frameBackgroundColour = Color.decode("#303030");
    private final static Color componentBackgroundColour = Color.decode("#3a3a3a");
    private final static Color fgColour = Color.white;

    /**
     * Définit le style des composants Swing
     * @param component
     */
    public static void setStyle(JComponent component){
        if(component.getClass().equals(JButton.class)){
            component.setBackground(componentBackgroundColour);
            component.setFocusable(false);
        } else{
            component.setBackground(frameBackgroundColour);
        }
        component.setForeground(fgColour);
    }

    /**
     * Définit le style général de la JFrame en paramètre
     * @param frame
     */
    public static void setStyle(JFrame frame){
        frame.setForeground(fgColour);
        frame.setBackground(frameBackgroundColour);
    }

    /**
     * Créé le style général des différentes JOptionPane qui apparaissent dans l'application
     */
    public static void setStyleJOptionPane(){
        /*UIManager.put("OptionPane.background", Color.decode("#3a3a3a"));
        UIManager.put("Panel.background", Color.decode("#3a3a3a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#fade55"));
        UIManager.put("Button.background", Color.decode("#424242"));
        UIManager.put("Button.foreground", Color.WHITE);*/
        UIManager.put("Button.focusable", false);
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
    }

}
