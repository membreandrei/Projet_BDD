package view;

import controller.Controller;
import model.ProgrammeurBean;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class ResultatView extends ViewPanel {

    private JScrollPane sp;
    private JTable table;
    private JButton searchButton = new JButton();
    private JButton deleteButton = new JButton();
    private JButton insertButton = new JButton();
    private JComboBox choice = new JComboBox();
    private JTextField searchText = new JTextField();

    public ResultatView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#303030")));
        this.setBackground(Color.decode("#424242"));
    }

    /**
     * Créé le panel pour l'affichage de tous les programmeurs
     *
     * @param informations
     */
    private void displayAll(TreeMap<Integer, ProgrammeurBean> informations) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        this.setHeaderTableau(informations, fm);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(0);
        this.add(sp);
    }

    /**
     * Créé le panel pour l'affichage précis lors d'un clic sur la liste de tous les programmeurs
     *
     * @param informations
     * @param valueComboBox
     */
    private void displayOne(TreeMap<Integer, ProgrammeurBean> informations, String valueComboBox) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);

        this.setHeaderTableau(informations, fm);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(0);
        this.add(this.setPanelRecherche(valueComboBox, false));
        this.add(sp);
        this.searchText.addActionListener(e -> searchButton.doClick());

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
    }


    /**
     * Lors d'un double clic sur un programmeur dans le tableau, ouvre une fenêtre permettant de modifier les données de ce dernier
     *
     * @param informations
     */
    private void modifyProg(TreeMap<Integer, ProgrammeurBean> informations) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        setHeaderTableau(informations, fm);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(1);
        this.add(this.setPanelRecherche(null, true));
        this.add(sp);

        this.searchText.addActionListener(e -> searchButton.doClick());

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
    }

    /**
     * Efface le(s) programmeur(s) sélectionné(s) dans le tableau
     *
     * @param informations
     */
    private void deleteProg(TreeMap<Integer, ProgrammeurBean> informations) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        setHeaderTableau(informations, fm);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(0);
        this.add(this.setPanelRecherche(null, true));
        this.add(sp);
        this.add(this.setPanelDeleteAjout(false));

        this.searchText.addActionListener(e -> searchButton.doClick());

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
        addListener(fm.getBasePanel().getController(), this.deleteButton);
    }

    /**
     * Permet tous les menus à la fois: Ajout/Modification/Suppression/Recherche
     *
     * @param informations
     * @param valueComboBox
     */
    private void allMenu(TreeMap<Integer, ProgrammeurBean> informations, String valueComboBox) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);

        this.setHeaderTableau(informations, fm);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(1);
        this.add(this.setPanelRecherche(valueComboBox, false));
        this.add(sp);
        this.add(this.setPanelDeleteAjout(true));

        this.searchText.addActionListener(e -> searchButton.doClick());

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
        addListener(fm.getBasePanel().getController(), this.deleteButton);
        addListener(fm.getBasePanel().getController(), this.insertButton);
    }

    /**
     * Gère le type de panel à ajouter en fonction du bouton cliqué du menu (dans MenuView)
     *
     * @param type
     * @param data
     * @param valueComboBox
     */
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
                allMenu(data, valueComboBox);
                break;
        }
    }

    /**
     * Créé la table contenant les programmeurs
     *
     * @param colNames
     * @param data
     */
    private void setTable(String[] colNames, Object[][] data) {
        this.table = new JTable(data, colNames) {
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
    }

    /**
     * Définit le scrollPanel lié au panel de vue
     */
    private void setSp() {
        this.sp = new JScrollPane(table);
        this.sp.setBorder(BorderFactory.createLineBorder(Color.decode("#303030"), 1));
        this.sp.getViewport().setBackground(Color.decode("#424242"));
        this.sp.setBackground(Color.decode("#424242"));
        this.sp.setBorder(new EmptyBorder(5, 10, 10, 10));
    }

    /**
     * Définit le header du tableau contenant les programmeurs, et ajoute le MouseListener à celle-ci
     *
     * @param informations
     * @param fm
     */
    private void setHeaderTableau(TreeMap<Integer, ProgrammeurBean> informations, FenetreMere fm) {
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

        this.setTable(colNames, data);
        this.table.addMouseListener(fm.getBasePanel().getController());
        this.setSp();
    }

    /**
     * Définit le style du champ de recherche des programmeurs
     */
    private void setSearchText() {
        this.searchText = new JTextField();
        this.searchText.setBackground(Color.decode("#3a3a3a"));
        this.searchText.setForeground(Color.WHITE);
        this.searchText.setPreferredSize(new Dimension(200, 20));
        this.searchText.setCaretColor(Color.WHITE);
        this.searchText.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1), new EmptyBorder(0, 5, 0, 5)));
    }

    /**
     * Même chose qu'au-dessus, mais pour le bouton de lancement de la recherche
     *
     * @param title
     */
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

    /**
     * Définit le choix du type de recherche (par liste déroulante)
     */
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

    /**
     * Définit le bouton de suppression d'un ou plusieurs programmeurs
     */
    private void setDeleteButton() {
        this.deleteButton = new JButton("Supprimer");
        this.deleteButton.setFocusable(false);
        this.deleteButton.setBackground(Color.decode("#3a3a3a"));
        this.deleteButton.setForeground(Color.WHITE);
        this.deleteButton.setPreferredSize(new Dimension(110, 20));
    }

    /**
     * Définit le bouton d'ajout d'un programmeur
     */
    private void setInsertButton() {
        this.insertButton = new JButton("Ajouter");
        this.insertButton.setFocusable(false);
        this.insertButton.setBackground(Color.decode("#3a3a3a"));
        this.insertButton.setForeground(Color.WHITE);
        this.insertButton.setPreferredSize(new Dimension(110, 20));
    }

    /**
     * Définit le panel comprenant à la fois l'ajout et la suppression d'un programmeur
     * (si ajout=true, alors seulement le bouton d'ajout sera présent)
     * @param ajout
     * @return
     */
    private JPanel setPanelDeleteAjout(boolean ajout) {
        JPanel jp = new JPanel();
        jp.setBackground(Color.decode("#424242"));
        jp.setPreferredSize(new Dimension(jp.getSize().width, 75));
        setDeleteButton();

        if (ajout) {
            setInsertButton();
            jp.add(insertButton);
        }
        jp.add(deleteButton);
        return jp;
    }

    /**
     * Définit le panel de recherche d'un programmeur
     * (en haut du tableau; contient le champ du texte d'entrée et par quoi rechercher, ainsi que le bouton)
     * @param valueComboBox
     * @param modify
     * @return
     */
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

    /**
     * Gère la recherche, à la fois la validation du contenu recherché et le résultat de ladite recherche
     * @param type
     */
    public void recherche(int type) {
        TreeMap<Integer, ProgrammeurBean> data = null;
        String choice = (String) this.getChoice().getSelectedItem();
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        Controller controller = fm.getBasePanel().getController();

        if (this.searchButton.getText().equals("Rechercher")) {
            if (validateInput(choice)) {
                switch (choice) {
                    case "Par ID":
                        data = rechercheId(controller);
                        break;

                    case "Par Nom":
                        data = controller.getProgrammeurByName(this.getSearchText().getText());
                        break;

                    case "Par Prénom":
                        data = controller.getProgrammeurByFirstName(this.getSearchText().getText());
                        break;

                    case "Par Année de naissance":
                        data = rechercheYear(controller);
                        break;
                }
            }
            this.modifyPanel(type, data, choice);
        } else {
            if (validateInput("Par ID")) {
                data = rechercheId(controller);
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez réessayer avec un nombre entier");
                data = controller.getProgrammeurs();
            }
            this.modifyPanel(type, data, null);
        }
    }

    /**
     * Valide l'input entré par l'utilisateur lors de la recherche
     * @param choice
     * @return
     */
    private Boolean validateInput(String choice) {
        if (choice.equals("Par ID") || choice.equals("Par Année de naissance")) {
            return (this.getSearchText().getText().matches("^([0-9]*\\p{L}*\\p{javaWhitespace}*)*+$|^$"));
        } else {
            return (this.getSearchText().getText().matches("^([a-zA-Z]*\\p{L}*\\p{javaWhitespace}*)*+$|^$"));
        }
    }

    /**
     * Gère la recherche par ID des programmeurs
     * @param controller
     * @return
     */
    private TreeMap<Integer, ProgrammeurBean> rechercheId(Controller controller) {
        TreeMap<Integer, ProgrammeurBean> data;
        if (this.getSearchText().getText().equals("")) {
            data = controller.getProgrammeurs();
        } else {
            try {
                data = controller.getProgrammeurById(Integer.parseInt(this.getSearchText().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Veuillez réessayer avec une recherche valide");
                data = controller.getProgrammeurs();
            }
        }
        return data;
    }

    /**
     * Gère la recherche par année de naissance des programmeurs
     * @param controller
     * @return
     */
    private TreeMap<Integer, ProgrammeurBean> rechercheYear(Controller controller) {
        TreeMap<Integer, ProgrammeurBean> data;
        if (this.getSearchText().getText().equals("")) {
            data = controller.getProgrammeurs();
        } else {
            data = controller.getProgrammeurByYear(Integer.parseInt(this.getSearchText().getText()));
        }
        return data;
    }

    /**
     * Récupère les lignes (=les programmeurs) sélectionnées par l'utilisateur dans le tableau
     * @return
     */
    public ArrayList<Integer> getIdRowSelected() {
        ArrayList<Integer> listeId = new ArrayList<>();
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

    public JButton getInsertButton() {
        return insertButton;
    }

    public JTextField getSearchText() {
        return searchText;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

}
