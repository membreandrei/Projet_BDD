package utils;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * Cette classe est d�di�e au style g�n�ral de l'application
 */
public final class StyleHelper {

    private final static Color frameBackgroundColour = Color.decode("#303030");
    private final static Color componentBackgroundColour = Color.decode("#3a3a3a");
    private final static Color fgColour = Color.white;

    /**
     * D�finit le style des composants Swing
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
     * D�finit le style g�n�ral de la JFrame en param�tre
     * @param frame
     */
    public static void setStyle(JFrame frame){
        frame.setForeground(fgColour);
        frame.setBackground(frameBackgroundColour);
    }

    /**
     * Cr�� le style g�n�ral des diff�rentes JOptionPane qui appara�ssent dans l'application
     */
    public static void setStyleJOptionPane(){
      /*  UIManager.put("OptionPane.background", Color.decode("#3a3a3a"));
        UIManager.put("Panel.background", Color.decode("#3a3a3a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#fade55"));
        UIManager.put("Button.background", Color.decode("#424242"));
        UIManager.put("Button.foreground", Color.WHITE);*/
        UIManager.put("Button.focusable", false);
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
    }

}
