package controller;

import com.opencsv.exceptions.CsvValidationException;
import model.ActionsBDDImpl;
import model.Media;
import model.Moment;
import model.TempsDeParole;
import org.apache.maven.shared.utils.StringUtils;
import org.sonatype.inject.Nullable;
import view.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.TreeMap;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

public class Controller implements ActionListener, MouseListener {
    private final MenuView mv;
    private ResultatView rv;
    private HashMap<String, JButton> identificator;
    private ActionsBDDImpl model;
    private ProgrammeurView pv;
    private Integer typeRv = 1;

    /**
     * Construit le contr�leur avec le BasePanel bp
     *
     * @param bp
     */
    public Controller(BasePanel bp) {
        this.mv = bp.getMv();
        this.rv = bp.getRv();
        this.fillHashMap();
        this.model = new ActionsBDDImpl();
    }

    /**
     * Remplit la HashMap avec les donn�es qui sont le nom des boutons
     */
    private void fillHashMap() {
        this.identificator = new HashMap<>();
        for (JButton button : this.mv.getAllButtons()) {
            this.identificator.put(button.getText(), button);
        }
    }

    /**
     * En fonction de l'ActionEvent e re�u en param�tre, agit diff�remment
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        TreeMap<Integer, Media> data = new TreeMap<>();
        if (e.getSource().equals(this.rv.getSearchButton())) {
            this.rv.recherche(this.typeRv);
        } else {
            if (e.getSource().equals(this.identificator.get("Afficher tous les médias"))) {
                data = this.model.getMedia();
                this.typeRv = 1;
            }
            if (e.getSource().equals(this.identificator.get("Supprimer un programmeur"))) {
                data = this.model.getMedia();
                this.typeRv = 3;
            }
            if (e.getSource().equals(this.identificator.get("Ajouter un programmeur"))) {
                data = this.model.getMedia();
                this.openModal(null, "add", true);
            }

            if (e.getActionCommand().equals("ajout")) {
                if (!validate()) {
                    JOptionPane.showMessageDialog(null, "Veuillez r�essayer avec des nombres dans salaire, prime, ann�e de naissance");
                } else {
                    if (this.model.createMedia(createProg(true)) == 0) {
                        JOptionPane.showMessageDialog(null, "Veuillez r�essayer avec des donn�es valides");
                        return;
                    }
                    FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor((Component) e.getSource());
                    fm.dispose();
                    data = this.model.getMedia();
                }
            }
            if (e.getActionCommand().equals("enregistrer")) {
                if (!validate()) {
                    JOptionPane.showMessageDialog(null, "Veuillez r�essayer avec des nombres dans salaire, prime, ann�e de naissance");
                } else {
                    this.model.editProg(createProg(false));
                    FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor((Component) e.getSource());
                    fm.dispose();
                    data = this.model.getMedia();
                }
            }

            if (e.getSource().equals(this.identificator.get("Modifier le salaire"))) {
                data = this.model.getMedia();
                this.typeRv = 2;
            }
            if (e.getSource().equals(this.identificator.get("Tous les menus"))) {
                data = this.model.getMedia();
                this.typeRv = 4;
            }
            if (e.getSource().equals(this.identificator.get("Importer à partir d'un csv"))) {
                getDataFromCsv(importFile());
            }
            if (e.getSource().equals(this.identificator.get("Quitter le programme"))) {
                System.exit(0);
            }
            if (e.getSource().equals(this.rv.getDeleteButton())) {
                deleteProg();
                data = this.model.getMedia();
            }
            if (e.getSource().equals(this.rv.getInsertButton())) {
                data = this.model.getMedia();
                this.openModal(null, "add", true);
            }
            this.rv.modifyPanel(this.typeRv, data, null);
        }
    }

    private String importFile() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choisir un fichier");
        FileNameExtensionFilter restrictCsv = new FileNameExtensionFilter("choisir fichier .csv", "csv");
        chooser.addChoosableFileFilter(restrictCsv);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getPath();
        }

        return null;
    }

    private void getDataFromCsv(String path){
        String csvFile = path;
        CSVReader reader = null;

        try {
            String str[];
            reader = new CSVReader(new FileReader(csvFile));
            reader.readNext();
            while ((str = reader.readNext()) != null) {

            }

            final int nbLigne = (int) reader.getLinesRead();
            new Thread(() -> {
                ProgressMonitor pm = new ProgressMonitor(this.rv, "progression en cours",
                        "Task starting", 0, nbLigne);

                pm.setMillisToDecideToPopup(100);
                pm.setMillisToPopup(100);

                CSVReader newReader = null;
                try {
                    newReader = new CSVReader(new FileReader(csvFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    newReader.readNext();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CsvValidationException e) {
                    e.printStackTrace();
                }
                String[] line = new String[0];
                String nomMediaPrec = "";

                while (true) {
                    try {
                        if (!((line = newReader.readNext()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CsvValidationException e) {
                        e.printStackTrace();
                    }
                    pm.setNote("donnée inséré: " + (int) newReader.getLinesRead() + " / " + nbLigne);
                    pm.setProgress((int) newReader.getLinesRead());
                    Media media = createMedia(line[0], line[1], line[2], Boolean.parseBoolean(line[3]));

                    if (!nomMediaPrec.equals(line[2])) {
                        nomMediaPrec = line[2];
                        this.model.createMedia(media);
                    }

                    Moment moment = createMoment(line[4], line[5], line[6], Boolean.parseBoolean(line[7]), Integer.parseInt(line[8]));
                    this.model.createMoment(moment);

                    media.setId(this.model.getMaxIdMedia());
                    moment.setId(this.model.getMaxIdMoment());

                    TempsDeParole tempsDeParole = createTempsDeParole(media, moment, Float.parseFloat(line[9]), Float.parseFloat(line[10]), Float.parseFloat(line[11]));
                    this.model.createTemspsDeParole(tempsDeParole);

                }
                pm.setNote("Task finished");
            }).start();


        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public Media createMedia(String type, String idIna, String nom, boolean estPublic) {
        Media media = new Media();
        media.setType(type);
        media.setIdIna(idIna);
        media.setNom(nom);
        media.setEstPublic(estPublic);

        return media;
    }

    public Moment createMoment(String dateMoment, String jour, String vacances, boolean estFerie, int heure) {
        Moment moment = new Moment();
        moment.setDateMoment(dateMoment);
        moment.setJour(jour);
        moment.setVacances(vacances);
        moment.setEstFerie(estFerie);
        moment.setHeure(heure);

        return moment;
    }

    public TempsDeParole createTempsDeParole(Media media, Moment moment, Float tempsHommes, Float tempsFemmes, Float tempsMusique) {
        TempsDeParole tempsDeParole = new TempsDeParole();
        tempsDeParole.setMedia(media);
        tempsDeParole.setMoment(moment);
        tempsDeParole.setTempsHommes(tempsHommes);
        tempsDeParole.setTempsFemmes(tempsFemmes);
        tempsDeParole.setTempsMusique(tempsMusique);

        return tempsDeParole;
    }

    /**
     * Efface un programmeur
     */
    private void deleteProg() {
        Object[] options = {"Supprimer", "Annuler"};
        int answer = JOptionPane.showOptionDialog(null, "�tes-vous s�r de votre choix ?", "Alerte Suppression",
                JOptionPane.YES_OPTION, JOptionPane.NO_OPTION,
                null, options, options[0]);
        if (answer == JOptionPane.YES_OPTION) {
            for (int id : this.rv.getIdRowSelected()) {
                this.model.deleteProg(id);
            }
        }
    }

    /**
     * Cr�� un ProgrammeurBean, vide ou non en fonction du param�tre ajout
     *
     * @param ajout
     * @return
     */
    private Media createProg(boolean ajout) {
        Media prog = new Media();

        prog.setNom(this.pv.getAllTextFields().get("nom").getText());
        /*prog.setPrenom(this.pv.getAllTextFields().get("pr�nom").getText());
        prog.setPseudo(this.pv.getAllTextFields().get("pseudo").getText());
        prog.setAdresse(this.pv.getAllTextFields().get("adresse").getText());
        prog.setAnNaissance(Integer.parseInt(this.pv.getAllTextFields().get("naissance").getText()));
        prog.setResponsable(this.pv.getAllTextFields().get("responsable").getText());
        prog.setHobby(this.pv.getAllTextFields().get("hobby").getText());
        prog.setSalaire(Float.parseFloat(this.pv.getAllTextFields().get("salaire").getText().replaceAll(",", ".")));
        prog.setPrime(Float.parseFloat(this.pv.getAllTextFields().get("prime").getText().replaceAll(",", ".")));*/
        if (!ajout) {
            prog.setId(Integer.parseInt((this.pv.getAllTextFields().get("id").getText())));
        }

        return prog;
    }

    /**
     * Valide les donn�es entr�es dans le cas de la cr�ation ou de la modification d'un programmeur
     *
     * @return
     */
    private boolean validate() {
        if (!StringUtils.isNumeric(this.pv.getAllTextFields().get("naissance").getText())) {
            return false;
        }
        if (!this.pv.getAllTextFields().get("prime").getText().matches("[-+]?[0-9]*[.|,]?[0-9]+")) {
            return false;
        }
        if (!this.pv.getAllTextFields().get("salaire").getText().matches("[-+]?[0-9]*[.|,]?[0-9]+")) {
            return false;
        }

        return true;
    }

    public void setPv(ProgrammeurView pv) {
        this.pv = pv;
    }

    /**
     * Ouvre la fen�tre d�taill�e d'un programmeur, modifiable ou non, d'ajout ou non
     *
     * @param pb
     * @param type
     * @param modify
     */
    private void openModal(@Nullable Media pb, String type, boolean modify) {
        String title;
        ProgrammeurView pv;
        if (pb == null) {
            title = "Ajout";
            pv = new ProgrammeurView();
        } else {
            //title = pb.getNom().toUpperCase() + " " + pb.getPrenom();
            pv = new ProgrammeurView(pb, modify);
        }
        new FenetreMere("title", pv, type);
    }

    public TreeMap<Integer, Media> getMedia() {
        return this.model.getMedia();
    }

    public TreeMap<Integer, Media> getMediaById(int id) {
        return this.model.getMediaById(id);
    }

    public TreeMap<Integer, Media> getMediaByName(String name) {
        System.out.println(name);
        return this.model.getMediaByName(name);
    }

    public TreeMap<Integer, Media> getProgrammeurByFirstName(String firstName) {
        return this.model.getProgrammeurByFirstName(firstName);
    }

    public TreeMap<Integer, Media> getProgrammeurByYear(Integer year) {
        return this.model.getProgrammeurByYear(year);
    }

    /**
     * Ouvre la modale d�taill�e d'un programmeur. Il est n�cessaire qu'il s'agisse d'un double clic
     * La fen�tre ouverte est soit en mode �dition avec les champs modifiables, ou bien en mode lecture seule
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        JTable laTable = (JTable) e.getSource();

        if (e.getClickCount() == 2) {

            //Ouvre la modal pour l'�dition d'un programmeur
            if (((DefaultCellEditor) laTable.getDefaultEditor(Object.class)).getClickCountToStart() == 1) {
                Object targetId = laTable.getValueAt(laTable.getSelectedRow(), laTable.getColumnModel().getColumnIndex("ID"));
                Media prog = this.model.getListeMedia().get(targetId);
                openModal(prog, "edit", true);
            }

            //Ouvre la modal en mode lecture uniquement
            if (((DefaultCellEditor) laTable.getDefaultEditor(Object.class)).getClickCountToStart() == 0) {
                Object targetId = laTable.getValueAt(laTable.getSelectedRow(), laTable.getColumnModel().getColumnIndex("ID"));
                Media prog = this.model.getListeMedia().get(targetId);
                openModal(prog, "display", false);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
