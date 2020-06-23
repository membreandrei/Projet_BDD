package view;

import controller.Controller;
import model.Media;
import model.TempsDeParole;
import org.apache.maven.shared.utils.StringUtils;

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
     * Créé le panel pour l'affichage des pourcentages des temps de parole
     *
     * @param informations
     */
    private void displayAll(TreeMap<Integer, TempsDeParole> informations) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        this.setHeaderTableauTempsDeParole(informations, fm);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(0);
        this.add(this.setPanelRecherche("Rechercher par année"));
        this.add(sp);


        this.searchText.addActionListener(e -> searchButton.doClick());

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
    }
    /**
     * Créé le panel pour l'affichage de la moyenne des temps de parole
     *
     * @param informations
     */
    private void displayMoyenneAll(TreeMap<Integer, TempsDeParole> informations) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        this.setHeaderTableauMoyenne(informations, fm);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(0);
        this.add(this.setPanelRecherche("Rechercher par média"));
        this.add(sp);


        this.searchText.addActionListener(e -> searchButton.doClick());

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
    }

    /**
     * Créé le panel pour l'affichage de la moyenne des temps de parole homme 2 fois supérieur à celle des femmes
     *
     * @param informations
     */
    private void displayMoyenne2FoisSup(TreeMap<Integer, TempsDeParole> informations) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        this.setHeaderTableauMoyenne2FoisSup(informations, fm);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(0);
        this.add(sp);
    }

    /**
     * Créé le panel pour l'affichage des pourcentages des temps de parole des hommes supérieur a un nombre X
     *
     * @param informations
     */
    private void displayPercentHSupX(TreeMap<Integer, TempsDeParole> informations) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        this.setHeaderTableauPercentHSupX(informations, fm);
        ((DefaultCellEditor) this.table.getDefaultEditor(Object.class)).setClickCountToStart(0);
        this.add(sp);

        this.add(this.setPanelRecherche("Recherche par pourcentage minimal"));
        this.add(sp);

        this.searchText.addActionListener(e -> searchButton.doClick());

        this.searchText.requestFocusInWindow();
        addListener(fm.getBasePanel().getController(), this.searchButton);
    }

    /**
     * Efface le(s) média(s) s�lectionn�(s) dans le tableau
     *
     * @param informations
     */
    private void deleteProg(TreeMap<Integer, Media> informations) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        this.setHeaderTableauMedia(informations, fm);
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
     * Permet tous les menus � la fois: Ajout/Modification/Suppression/Recherche pour tout les médias
     *
     * @param informations
     * @param valueComboBox
     */
    private void allMenu(TreeMap<Integer, Media> informations, String valueComboBox) {
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);

        this.setHeaderTableauMedia(informations, fm);
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
     * Gère le type de panel � ajouter en fonction du bouton cliqu� du menu (dans MenuView) pour les temps de paroles
     *
     * @param dataTDP
     * @param type
     */
    public void modifyPanel(Integer type, TreeMap<Integer, TempsDeParole> dataTDP) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        switch (type) {
            case 1:
                displayMoyenneAll(dataTDP);
                break;
            case 2:
                displayAll(dataTDP);
                break;
            case 3:
                displayMoyenne2FoisSup(dataTDP);
                break;
            case 5:
                displayPercentHSupX(dataTDP);
                break;
        }

    }

    /**
     * Gère le type de panel � ajouter en fonction du bouton cliqu� du menu (dans MenuView) pour les temps de paroles
     *
     * @param data
     * @param type
     * @param valueComboBox
     */
    public void modifyPanel(Integer type, TreeMap<Integer, Media> data, String valueComboBox) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        switch (type) {
            case 3:
                deleteProg(data);
                break;
            case 4:
                allMenu(data, valueComboBox);
                break;
        }
    }

    /**
     * Cr�� la table contenant des média / temps de parole
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

        if (this.table.getColumnName(0).contains("id")) {
            this.table.removeColumn(table.getColumnModel().getColumn(0));
        }

        this.table.setBackground(Color.decode("#424242"));
        this.table.setForeground(Color.white);
        this.table.setFocusable(false);
        this.table.getTableHeader().setBackground(Color.decode("#424242"));
        this.table.getTableHeader().setForeground(Color.white);
        this.table.setBorder(new MatteBorder(0, 1, 1, 1, Color.WHITE));
        this.table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    }

    /**
     * D�finit le scrollPanel li� au panel de vue
     */
    private void setSp() {
        this.sp = new JScrollPane(table);
        this.sp.setBorder(BorderFactory.createLineBorder(Color.decode("#303030"), 1));
        this.sp.getViewport().setBackground(Color.decode("#424242"));
        this.sp.setBackground(Color.decode("#424242"));
        this.sp.setBorder(new EmptyBorder(5, 10, 10, 10));
    }

    /**
     * D�finit le header du tableau contenant les médias, et ajoute le MouseListener � celle-ci
     *
     * @param informations
     * @param fm
     */
    private void setHeaderTableauMedia(TreeMap<Integer, Media> informations, FenetreMere fm) {
        String[] colNames = {"id_media", "Nom", "Type", "INA", "Public ?"};
        Object[][] data = new Object[informations.size()][5];
        int index = 0;
        for (Integer key : informations.keySet()) {
            data[index][0] = informations.get(key).getId();
            data[index][1] = informations.get(key).getNom();
            data[index][2] = informations.get(key).getType();
            data[index][3] = informations.get(key).getIdIna();
            data[index][4] = (informations.get(key).getEstPublic() ? "Oui" : "Non");
            index++;
        }

        this.setTable(colNames, data);
        this.table.addMouseListener(fm.getBasePanel().getController());
        this.setSp();
    }
    /**
     * D�finit le header du tableau contenant les temps de parole, et ajoute le MouseListener � celle-ci
     *
     * @param informations
     * @param fm
     */
    private void setHeaderTableauTempsDeParole(TreeMap<Integer, TempsDeParole> informations, FenetreMere fm) {
        String[] colNames = {"Année", "% Femmes", "% Hommes", "% Musique"};
        Object[][] data = new Object[informations.size()][4];
        int index = 0;
        for (Integer key : informations.keySet()) {
            data[index][0] = informations.get(key).getMoment().getDateMoment();
            data[index][1] = informations.get(key).getTempsFemmes();
            data[index][2] = informations.get(key).getTempsHommes();
            data[index][3] = informations.get(key).getTempsMusique();
            index++;
        }

        this.setTable(colNames, data);
        this.table.addMouseListener(fm.getBasePanel().getController());
        this.setSp();
    }

    /**
     * D�finit le header du tableau contenant les moyennes des temps de parole, et ajoute le MouseListener � celle-ci
     *
     * @param informations
     * @param fm
     */
    private void setHeaderTableauMoyenne(TreeMap<Integer, TempsDeParole> informations, FenetreMere fm) {
        String[] colNames = {"Nom", "Année", "Moyenne Femmes (s)", "Moyenne Hommes (s)", "Moyenne Musique (s)"};
        Object[][] data = new Object[informations.size()][5];
        int index = 0;
        for (Integer key : informations.keySet()) {
            data[index][0] = informations.get(key).getMedia().getNom();
            data[index][1] = informations.get(key).getMoment().getDateMoment();
            data[index][2] = informations.get(key).getTempsFemmes();
            data[index][3] = informations.get(key).getTempsHommes();
            data[index][4] = informations.get(key).getTempsMusique();
            index++;
        }

        this.setTable(colNames, data);
        this.table.addMouseListener(fm.getBasePanel().getController());
        this.setSp();
    }

    /**
     * D�finit le header du tableau contenant les moyennes hommes 2 fois supérieur a celle de la femme, et ajoute le MouseListener � celle-ci
     *
     * @param informations
     * @param fm
     */
    private void setHeaderTableauMoyenne2FoisSup(TreeMap<Integer, TempsDeParole> informations, FenetreMere fm) {
        String[] colNames = {"Nom", "Moyenne Femmes (s)", "Moyenne Hommes (s)"};
        Object[][] data = new Object[informations.size()][3];
        int index = 0;
        for (Integer key : informations.keySet()) {
            data[index][0] = informations.get(key).getMedia().getNom();
            data[index][1] = informations.get(key).getTempsFemmes();
            data[index][2] = informations.get(key).getTempsHommes();
            index++;
        }

        this.setTable(colNames, data);
        this.table.addMouseListener(fm.getBasePanel().getController());
        this.setSp();
    }

    /**
     * D�finit le header du tableau contenant les pourcentage Homme supérieur a un nombre donné, et ajoute le MouseListener � celle-ci
     *
     * @param informations
     * @param fm
     */
    private void setHeaderTableauPercentHSupX(TreeMap<Integer, TempsDeParole> informations, FenetreMere fm) {
        String[] colNames = {"Nom", "Type", "% Femmes", "% Hommes", "% Musique"};
        Object[][] data = new Object[informations.size()][5];
        int index = 0;
        for (Integer key : informations.keySet()) {
            data[index][0] = informations.get(key).getMedia().getNom();
            data[index][1] = informations.get(key).getMedia().getType();
            data[index][2] = informations.get(key).getTempsFemmes();
            data[index][3] = informations.get(key).getTempsHommes();
            data[index][4] = informations.get(key).getTempsMusique();
            index++;
        }

        this.setTable(colNames, data);
        this.table.addMouseListener(fm.getBasePanel().getController());
        this.setSp();
    }


    /**
     * D�finit le style du champ de recherche des média/temps de parole
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
     * M�me chose qu'au-dessus, mais pour le bouton de lancement de la recherche
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
            this.searchButton.setPreferredSize(new Dimension(190, 20));
    }

    /**
     * D�finit le choix du type de recherche (par liste d�roulante)
     */
    private void setChoice() {
        String[] elements = new String[]{"Par ID", "Par Nom"};
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
     * D�finit le bouton de suppression d'un ou plusieurs médias
     */
    private void setDeleteButton() {
        this.deleteButton = new JButton("Supprimer");
        this.deleteButton.setFocusable(false);
        this.deleteButton.setBackground(Color.decode("#3a3a3a"));
        this.deleteButton.setForeground(Color.WHITE);
        this.deleteButton.setPreferredSize(new Dimension(110, 20));
    }

    /**
     * D�finit le bouton d'ajout d'un média
     */
    private void setInsertButton() {
        this.insertButton = new JButton("Ajouter");
        this.insertButton.setFocusable(false);
        this.insertButton.setBackground(Color.decode("#3a3a3a"));
        this.insertButton.setForeground(Color.WHITE);
        this.insertButton.setPreferredSize(new Dimension(110, 20));
    }

    /**
     * D�finit le panel comprenant � la fois l'ajout et la suppression d'un média
     * (si ajout=true, alors seulement le bouton d'ajout sera pr�sent)
     *
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
     * D�finit le panel de recherche des médias
     * (en haut du tableau; contient le champ du texte d'entr�e et par quoi rechercher, ainsi que le bouton)
     *
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
     * D�finit le panel de recherche des temps de parole
     * (en haut du tableau; contient le champ du texte d'entr�e et par quoi rechercher, ainsi que le bouton)
     *
     * @param titre
     * @return
     */
    private JPanel setPanelRecherche(String titre) {
        JPanel jp = new JPanel();
        jp.setBackground(Color.decode("#424242"));
        jp.setPreferredSize(new Dimension(jp.getSize().width, 0));

        this.setSearchText();
        this.setSearchButton(titre);
        jp.add(this.searchText);
        jp.add(this.searchButton);

        return jp;
    }


    /**
     * G�re la recherche, � la fois la validation du contenu recherch� et le r�sultat de ladite recherche
     *
     * @param type
     */
    public void recherche(int type) {
        TreeMap<Integer, Media> data = null;
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
                        data = controller.getMediaByName(this.getSearchText().getText());
                        break;
                }
            }
            this.modifyPanel(type, data, choice);
        } else {
            if (validateInput("Par ID")) {
                data = rechercheId(controller);
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez r�essayer avec un nombre entier");
                data = controller.getMedia();
            }
            this.modifyPanel(type, data, null);
        }
    }

    /**
     * G�re la recherche pour année, � la fois la validation du contenu recherch� et le r�sultat de ladite recherche
     *
     *
     */
    public void rechercheParAnnee() {
        TreeMap<Integer, TempsDeParole> data = null;
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        Controller controller = fm.getBasePanel().getController();
        if(this.getSearchText().getText().equals("")){
            JOptionPane.showMessageDialog(null, "Veuillez réessayer avec un nombre entier");
            data = controller.getPourcentageTDP();
            this.modifyPanel(2, data);
            return;
        }
        if (this.searchButton.getText().equals("Rechercher par année")) {
            if (validateInput()) {
                data = controller.getPourcentageTDPByYear(Integer.parseInt(this.getSearchText().getText()));
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez réessayer avec un nombre entier");
                data = controller.getPourcentageTDP();
            }
            this.modifyPanel(2, data);
        }
    }

    /**
     * G�re la recherche par média, � la fois la validation du contenu recherch� et le r�sultat de ladite recherche
     *
     *
     */
    public void rechercheParMedia() {
        TreeMap<Integer, TempsDeParole> data = null;
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        Controller controller = fm.getBasePanel().getController();
        if(this.getSearchText().getText().equals("")){
            JOptionPane.showMessageDialog(null, "Veuillez réessayer en écrivant quelque chose");
            data = controller.getMoyenneTDP();
            this.modifyPanel(1, data);
            return;
        }
        if (this.searchButton.getText().equals("Rechercher par média")) {
            data = controller.getMoyenneTDPByMedia(this.getSearchText().getText());
        }
        this.modifyPanel(1, data);
    }
    /**
     * G�re la recherche par un nombre compris entre 0 et 100, � la fois la validation du contenu recherch� et le r�sultat de ladite recherche
     *
     *
     */
    public void recherchePourcentageMinimalH(){
        TreeMap<Integer, TempsDeParole> data = null;
        FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor(this);
        Controller controller = fm.getBasePanel().getController();

        if (this.searchButton.getText().equals("Recherche par pourcentage minimal")) {
            if(this.getSearchText().getText().equals("")){
                JOptionPane.showMessageDialog(null, "Veuillez réessayer (nombre entier, compris entre 0 et 100 inclus");
                data = controller.getTVPourcentageHommeSupX(0);
                this.modifyPanel(5, data);
                return;
            }
            if (validateInput() && 0 <= Integer.parseInt(this.getSearchText().getText()) && Integer.parseInt(this.getSearchText().getText()) <= 100) {
                data = controller.getTVPourcentageHommeSupX(Integer.parseInt(this.getSearchText().getText()));
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez réessayer (nombre entier, compris entre 0 et 100 inclus");
                data = controller.getTVPourcentageHommeSupX(0);
            }
            this.modifyPanel(5, data);
        }
    }


    /**
     * Valide l'input entr� par l'utilisateur lors de la recherche
     *
     * @param choice
     * @return
     */
    private Boolean validateInput(String choice) {
        if (choice.equals("Par ID") || choice.equals("Par Ann�e de naissance")) {
            return (this.getSearchText().getText().matches("^([0-9]*\\p{L}*\\p{javaWhitespace}*)*+$|^$"));
        } else {
            return (this.getSearchText().getText().matches("^([a-zA-Z]*\\p{L}*\\p{javaWhitespace}*)*+$|^$"));
        }
    }

    /**
     * Valide l'input entr� par l'utilisateur lors de la recherche
     *
     * @return
     */
    private Boolean validateInput() {
        return (StringUtils.isNumeric(this.getSearchText().getText()));
    }

    /**
     * G�re la recherche par ID des médias
     *
     * @param controller
     * @return
     */
    private TreeMap<Integer, Media> rechercheId(Controller controller) {
        TreeMap<Integer, Media> data;
        if (this.getSearchText().getText().equals("")) {
            data = controller.getMedia();
        } else {
            try {
                data = controller.getMediaById(Integer.parseInt(this.getSearchText().getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Veuillez r�essayer avec une recherche valide");
                data = controller.getMedia();
            }
        }
        return data;
    }


    /**
     * R�cup�re les lignes (=les médias) s�lectionn�es par l'utilisateur dans le tableau
     *
     * @return
     */
    public ArrayList<Integer> getIdRowSelected() {
        ArrayList<Integer> listeId = new ArrayList<>();
        for (int i = 0; i < this.table.getSelectedRows().length; i++) {
            Object targetId = this.table.getModel().getValueAt(this.table.getSelectedRows()[i], 0);
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
