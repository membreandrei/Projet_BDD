package view;

import javax.swing.*;
import java.awt.*;

public class ResultatView extends ViewPanel {

    private JTextArea resultArea = new JTextArea("");
    private JScrollPane sp;

    public ResultatView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#303030")));
        this.resultArea.setMargin(new Insets(10,10,10,10));
        this.resultArea.setBackground(Color.decode("#424242"));
        this.resultArea.setForeground(Color.white);
        this.resultArea.setLineWrap(true);
        this.resultArea.setEditable(false);
        sp = new JScrollPane(resultArea);
        //sp.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        addComponent(sp);
    }

    public void editText(String text){
        this.resultArea.setText(text);
        this.resultArea.setCaretPosition(0);
    }

}
