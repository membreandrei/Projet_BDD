package view;

import model.ProgrammeurBean;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.WeakHashMap;

public class ResultatView extends ViewPanel {

    private JTextArea resultArea = new JTextArea("");
    private JScrollPane sp;
    private JTable table;

    public ResultatView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#303030")));
        this.setBackground(Color.decode("#424242"));
    }

    public void editText(String text) {
        this.resultArea.setText(text);
        this.resultArea.setCaretPosition(0);
    }

    public JTable getTable() {
        return table;
    }

    private void displayAll() {
        this.resultArea.setText("");
        this.resultArea.setMargin(new Insets(10, 10, 10, 10));
        this.resultArea.setBackground(Color.decode("#424242"));
        this.resultArea.setForeground(Color.white);
        this.resultArea.setLineWrap(true);
        this.resultArea.setEditable(false);
        sp = new JScrollPane(resultArea);
        sp.setBorder(BorderFactory.createLineBorder(Color.decode("#303030")));
        addComponent(sp);
    }

    private void displayOne(WeakHashMap<Integer, ProgrammeurBean> informations) {
        String[] colNames = {"", "ID", "NOM", "PRENOM"};
        Object[][] data = new Object[informations.size()][4];
        int index = 0;
        for (Integer key : informations.keySet()) {
            data[index][0] = "//////";
            data[index][1] = informations.get(key).getId();
            data[index][2] = informations.get(key).getNom().toUpperCase();
            data[index][3] = informations.get(key).getPrenom();
            index++;
        }

        table = new JTable(data, colNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setBackground(Color.decode("#424242"));
        table.setForeground(Color.white);
        table.setFocusable(false);
        table.getTableHeader().setBackground(Color.decode("#424242"));
        table.getTableHeader().setForeground(Color.white);
        table.setBorder(new MatteBorder(0,1,1,1,Color.WHITE));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE,1));

        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        table.addMouseListener(fm.getBasePanel().getController());

        //this.addListener(this.getParent());

        sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(Color.decode("#303030"),1));
        sp.getViewport().setBackground(Color.decode("#424242"));
        sp.setBackground(Color.decode("#424242"));
        sp.setBorder(new EmptyBorder(10,10,10,10));

        this.add(sp);
    }

    private void deleteOne() {

    }

    private void addOne() {

    }

    private void editWage() {

    }

    public void modifyPanel(Integer type, WeakHashMap<Integer, ProgrammeurBean> data) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        //System.out.println(data.get(0).getNom());
        switch (type) {
            case 0:
                displayAll();
                break;
            case 1:
                displayOne(data);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
        }
    }

}
