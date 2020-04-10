package view;

import controller.Controller;
import model.ProgrammeurBean;
import org.sonatype.inject.Nullable;
import utils.StyleHelper;

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
import java.util.ArrayList;
import java.util.TreeMap;

public class ResultatView extends ViewPanel {

    private JTextArea resultArea = new JTextArea("");
    private JScrollPane sp;
    private JTable table;
    private JButton searchButton = new JButton();
    private JButton deleteButton = new JButton();
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

    private void displayAll(TreeMap<Integer, ProgrammeurBean> informations) {
        /* Ancien code
        this.resultArea.setText("");
        this.resultArea.setMargin(new Insets(10, 10, 10, 10));
        this.resultArea.setBackground(Color.decode("#424242"));
        this.resultArea.setForeground(Color.white);
        this.resultArea.setLineWrap(true);
        this.resultArea.setEditable(false);
        sp = new JScrollPane(resultArea);
        sp.setBorder(BorderFactory.createLineBorder(Color.decode("#303030")));
        addComponent(sp);*/

        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        this.setPaneTableau(informations, fm, false);
        this.add(sp);
    }

    private void displayOne(TreeMap<Integer, ProgrammeurBean> informations, String valueComboBox) {

        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);

        this.setPaneTableau(informations, fm, false);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(0);
        this.add(this.setPanelRecherche(valueComboBox, false));
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

    private void editWage() {

    }

    private void modifyProg(TreeMap<Integer, ProgrammeurBean> informations) {

        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        setPaneTableau(informations, fm, true);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(1);
        this.add(this.setPanelRecherche(null, true));
        this.add(sp);

        this.searchText.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                searchButton.doClick();

            }
        });

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
    }

    private void deleteProg(TreeMap<Integer, ProgrammeurBean> informations) {

        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        setPaneTableau(informations, fm, false);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(0);
        this.add(this.setPanelRecherche(null, true));
        this.add(sp);
        this.add(this.setDeleteButton());

        this.searchText.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                searchButton.doClick();

            }
        });

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
        addListener(fm.getBasePanel().getController(), this.deleteButton);
    }

    public void modifyPanel(Integer type, TreeMap<Integer, ProgrammeurBean> data, String valueComboBox) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        switch (type) {
            case 0:
                displayAll(data);
                break;
            case 1:
                displayOne(data, valueComboBox);
                break;
            case 2:
                modifyProg(data);
                break;
            case 3:
                deleteProg(data);
                break;
            case 4:
            case 5:
        }
    }

    private void setTable(String[] colNames, Object[][] data, boolean modify) {

        if (!modify) {
            this.table = new JTable(data, colNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        } else {
            this.table = new JTable(data, colNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    if (table.getColumnModel().getColumnIndex("ID") == column)
                        return false;
                    else
                        return true;
                }
            };
        }

        this.table.setBackground(Color.decode("#424242"));
        this.table.setForeground(Color.white);
        this.table.setFocusable(false);
        this.table.getTableHeader().setBackground(Color.decode("#424242"));
        this.table.getTableHeader().setForeground(Color.white);
        this.table.setBorder(new MatteBorder(0, 1, 1, 1, Color.WHITE));
        this.table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    }

    private void setSp() {
        this.sp = new JScrollPane(table);
        this.sp.setBorder(BorderFactory.createLineBorder(Color.decode("#303030"), 1));
        this.sp.getViewport().setBackground(Color.decode("#424242"));
        this.sp.setBackground(Color.decode("#424242"));
        this.sp.setBorder(new EmptyBorder(5, 10, 10, 10));
    }

    private void setPaneTableau(TreeMap<Integer, ProgrammeurBean> informations, FenetreMere fm, boolean modify) {

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

        this.setTable(colNames, data, modify);

        this.table.addMouseListener(fm.getBasePanel().getController());

        this.setSp();

    }

    private void setSearchText() {
        this.searchText = new JTextField();
        this.searchText.setBackground(Color.decode("#3a3a3a"));
        this.searchText.setForeground(Color.WHITE);
        this.searchText.setPreferredSize(new Dimension(200, 20));
        this.searchText.setCaretColor(Color.WHITE);
        this.searchText.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), new EmptyBorder(0, 5, 0, 5)));
    }

    private void setSearchButton(String title) {
        this.searchButton = new JButton(title);
        this.searchButton.setFocusable(false);
        this.searchButton.setBackground(Color.decode("#3a3a3a"));
        this.searchButton.setForeground(Color.WHITE);
        if (title.equals("Rechercher"))
            this.searchButton.setPreferredSize(new Dimension(110, 20));
        else
            this.searchButton.setPreferredSize(new Dimension(150, 20));
    }

    private void setChoice() {
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
    }

    private JPanel setDeleteButton() {
        JPanel jp = new JPanel();
        jp.setBackground(Color.decode("#424242"));
        jp.setPreferredSize(new Dimension(jp.getSize().width, 200));
        this.deleteButton = new JButton("Supprimer");
        this.deleteButton.setFocusable(false);
        this.deleteButton.setBackground(Color.decode("#3a3a3a"));
        this.deleteButton.setForeground(Color.WHITE);
        this.deleteButton.setPreferredSize(new Dimension(110, 20));
        jp.add(deleteButton);
        return jp;
    }

    private JPanel setPanelRecherche(String valueComboBox, boolean modify) {
        JPanel jp = new JPanel();
        jp.setBackground(Color.decode("#424242"));
        jp.setPreferredSize(new Dimension(jp.getSize().width, 70));

        this.setSearchText();
        if (!modify) {
            this.setSearchButton("Rechercher");
        } else {
            this.setSearchButton("Rechercher par ID");
        }
        jp.add(this.searchText);
        jp.add(this.searchButton);

        if (!modify) {
            this.setChoice();

            if (valueComboBox != null)
                this.choice.setSelectedItem(valueComboBox);

            for (int i = 0; i < this.choice.getComponentCount(); i++) {
                if (this.choice.getComponent(i) instanceof JComponent) {
                    ((JComponent) this.choice.getComponent(i)).setBorder(new EmptyBorder(0, 0, 0, 0));
                }
            }
            jp.add(this.choice);
        }

        return jp;
    }

    public void recherche() {
        TreeMap<Integer, ProgrammeurBean> data = null;
        String choice = (String) this.getChoice().getSelectedItem();
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        Controller controller = fm.getBasePanel().getController();

        if (this.searchButton.getText().equals("Rechercher")) {
            if (validateInput(choice)) {
                switch (choice) {
                    case "Par ID":
                        data = rechercheId(controller, data);

                        break;

                    case "Par Nom":
                        data = controller.getProgrammeurByName(this.getSearchText().getText());
                        break;

                    case "Par Prénom":
                        data = controller.getProgrammeurByFirstName(this.getSearchText().getText());
                        break;

                    case "Par Année de naissance":
                        data = rechercheYear(controller, data);
                        break;
                }
            } else {

            }
            this.modifyPanel(1, data, choice);
        } else {
            if (validateInput("Par ID")) {
                data = rechercheId(controller, data);
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez réessayer avec un nombre entier");
                data = controller.getProgrammeurs();
            }
            this.modifyPanel(2, data, null);
        }
    }

    private Boolean validateInput(String choice) {
        if (choice.equals("Par ID") || choice.equals("Par Année de naissance")) {
            return (this.getSearchText().getText().matches("^[0-9]+$|^$"));
        } else {
            return (this.getSearchText().getText().matches("^[a-zA-Z]+$|^$"));
        }
    }

    private TreeMap<Integer, ProgrammeurBean> rechercheId(Controller controller, TreeMap<Integer, ProgrammeurBean> data) {
        if (this.getSearchText().getText().equals("")) {
            data = controller.getProgrammeurs();
        } else {
            data = controller.getProgrammeurById(Integer.parseInt(this.getSearchText().getText()));
        }
        return data;
    }

    private TreeMap<Integer, ProgrammeurBean> rechercheYear(Controller controller, TreeMap<Integer, ProgrammeurBean> data) {
        if (this.getSearchText().getText().equals("")) {
            data = controller.getProgrammeurs();
        } else {
            data = controller.getProgrammeurByYear(Integer.parseInt(this.getSearchText().getText()));
        }
        return data;
    }

    public ArrayList<Integer> getIdRowSelected() {
        ArrayList<Integer> listeId = new ArrayList<Integer>();
        for (int i = 0; i < this.table.getSelectedRows().length; i++) {
            Object targetId = this.table.getValueAt(this.table.getSelectedRows()[i], this.table.getColumnModel().getColumnIndex("ID"));
            listeId.add((int) targetId);
        }
        return listeId;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getDeleteButton() {
        return deleteButton;
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
