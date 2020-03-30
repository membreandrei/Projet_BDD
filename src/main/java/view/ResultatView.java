package view;

import javax.swing.*;
import java.awt.*;

public class ResultatView extends ViewPanel {

    private JTextArea resultArea = new JTextArea("Texte de base");

    public ResultatView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        resultArea.setSize(500, 500);
        this.setBorder(BorderFactory.createLineBorder(Color.blue));
        addComponent(resultArea);
    }
}
