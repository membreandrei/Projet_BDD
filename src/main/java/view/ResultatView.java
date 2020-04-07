package view;

import controller.Controller;
import model.ProgrammeurBean;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;

public class ResultatView extends ViewPanel {

    private JTextArea resultArea = new JTextArea("");
    private JScrollPane sp;
    private JTable table;
    private JButton searchButton = new JButton();
    private JComboBox choice = new JComboBox();
    private JTextField searchText = new JTextField();

    public ResultatView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#303030")));
        this.setBackground(Color.decode("#424242"));
    }

    public void editText(String text) {
        this.resultArea.setText(text);
        this.resultArea.setCaretPosition(0);
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

    private void displayOne(TreeMap<Integer, ProgrammeurBean> informations, String valueComboBox) {

        JPanel jp = new JPanel();
        jp.setBackground(Color.decode("#424242"));
        jp.setPreferredSize(new Dimension(jp.getSize().width, 70));

        this.searchText = new JTextField();
        this.searchText.setBackground(Color.decode("#3a3a3a"));
        this.searchText.setForeground(Color.WHITE);
        this.searchText.setPreferredSize(new Dimension(200, 20));
        this.searchText.setCaretColor(Color.WHITE);
        this.searchText.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), new EmptyBorder(0, 5, 0, 5)));

        this.searchButton = new JButton("Rechercher");
        this.searchButton.setFocusable(false);
        this.searchButton.setBackground(Color.decode("#3a3a3a"));
        this.searchButton.setForeground(Color.WHITE);
        this.searchButton.setPreferredSize(new Dimension(110, 20));

        String[] elements = new String[]{"Par ID", "Par Nom", "Par Prénom", "Par Année de naissance"};
        this.choice = new JComboBox(elements);
        this.choice.setBackground(Color.decode("#3a3a3a"));
        this.choice.setForeground(Color.WHITE);
        this.choice.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.choice.setFocusable(false);
        this.choice.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                BasicArrowButton result = new BasicArrowButton(BasicArrowButton.SOUTH);
                result.setFocusable(false);
                result.setEnabled(false);
                result.setBackground(Color.decode("#3a3a3a")); //---button's color
                return result;
            }
        });

        if (valueComboBox != null)
            this.choice.setSelectedItem(valueComboBox);

        for (int i = 0; i < this.choice.getComponentCount(); i++) {
            if (this.choice.getComponent(i) instanceof JComponent) {
                ((JComponent) this.choice.getComponent(i)).setBorder(new EmptyBorder(0, 0, 0, 0));
            }
        }

        jp.add(this.searchText);
        jp.add(this.searchButton);
        jp.add(this.choice);

        String[] colNames = {"ID", "NOM", "PRENOM", "ANNEE DE NAISSANCE", "SALAIRE", "PSEUDO"};
        Object[][] data = new Object[informations.size()][6];
        int index = 0;
        for (Integer key : informations.keySet()) {
            data[index][0] = informations.get(key).getId();
            data[index][1] = informations.get(key).getNom().toUpperCase();
            data[index][2] = informations.get(key).getPrenom();
            data[index][3] = informations.get(key).getAnNaissance();
            data[index][4] = informations.get(key).getSalaire();
            data[index][5] = informations.get(key).getPseudo();
            index++;
        }

        table = new JTable(data, colNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.table.setBackground(Color.decode("#424242"));
        this.table.setForeground(Color.white);
        this.table.setFocusable(false);
        this.table.getTableHeader().setBackground(Color.decode("#424242"));
        this.table.getTableHeader().setForeground(Color.white);
        this.table.setBorder(new MatteBorder(0, 1, 1, 1, Color.WHITE));
        this.table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        this.table.addMouseListener(fm.getBasePanel().getController());

        this.sp = new JScrollPane(table);
        this.sp.setBorder(BorderFactory.createLineBorder(Color.decode("#303030"), 1));
        this.sp.getViewport().setBackground(Color.decode("#424242"));
        this.sp.setBackground(Color.decode("#424242"));
        this.sp.setBorder(new EmptyBorder(1, 10, 10, 10));

        this.add(jp);
        this.add(sp);

        this.searchText.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                searchButton.doClick();

            }
        });

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
    }

    private void deleteOne() {

    }

    private void addOne() {

    }

    private void editWage() {

    }

    public void modifyPanel(Integer type, TreeMap<Integer, ProgrammeurBean> data, String valueComboBox) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        switch (type) {
            case 0:
                displayAll();
                break;
            case 1:
                displayOne(data, valueComboBox);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
        }
    }


    public void recherche() {
        JOptionPane jo = new JOptionPane();
        jo.setBackground(Color.BLUE);
        UIManager.put("OptionPane.background", Color.decode("#3a3a3a"));
        UIManager.put("Panel.background", Color.decode("#3a3a3a"));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", Color.decode("#424242"));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focusable", false);
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

        TreeMap<Integer, ProgrammeurBean> data = null;
        String choice = (String) this.getChoice().getSelectedItem();
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        Controller controller = fm.getBasePanel().getController();

        if (validateInput(choice)) {
            switch (choice) {
                case "Par ID":
                    data = rechercheId(controller, data, jo);
                    break;

                case "Par Nom":
                    data = controller.getProgrammeurByName(this.getSearchText().getText());
                    break;

                case "Par Prénom":
                    data = controller.getProgrammeurByFirstName(this.getSearchText().getText());
                    break;

                case "Par Année de naissance":
                    rechercheYear(controller, data, jo);
                    break;
            }
        } else {
            jo.showMessageDialog(null, "Veuillez réessayer avec un nombre entier");
            data = controller.getProgrammeurs();
        }
        this.modifyPanel(1, data, choice);
    }

    private Boolean validateInput(String choice) {
        if (choice.equals("Par ID") || choice.equals("Par Année de naissance")) {
            return (this.getSearchText().getText().matches("^[0-9]+$|^$"));
        } else {
            return (this.getSearchText().getText().matches("^[a-zA-Z]+$|^$"));
        }
    }

    private TreeMap<Integer, ProgrammeurBean> rechercheId(Controller controller, TreeMap<Integer, ProgrammeurBean> data, JOptionPane jo) {
        if (this.getSearchText().getText().equals("")) {
            data = controller.getProgrammeurs();
        } else {
            data = controller.getProgrammeurById(Integer.parseInt(this.getSearchText().getText()));
        }
        return data;
    }

    private TreeMap<Integer, ProgrammeurBean> rechercheYear(Controller controller, TreeMap<Integer, ProgrammeurBean> data, JOptionPane jo) {
        if (this.getSearchText().getText().equals("")) {
            data = controller.getProgrammeurs();
        } else {
            data = controller.getProgrammeurByYear(Integer.parseInt(this.getSearchText().getText()));
        }
        return data;
    }


    public JTable getTable() {
        return table;
    }

    public JComboBox getChoice() {
        return choice;
    }

    public JTextField getSearchText() {
        return searchText;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

}
