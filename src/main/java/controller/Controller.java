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
    private final ResultatView rv;
    private HashMap<String, JButton> identificator;
    private final ActionsBDDImpl model;
    private MediaView pv;
    private Integer typeRv = 4;

    /**
     * Construit le contrôleur avec le BasePanel bp
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
     * Remplit la HashMap avec les données qui sont le nom des boutons
     */
    private void fillHashMap() {
        this.identificator = new HashMap<>();
        for (JButton button : this.mv.getAllButtons()) {
            this.identificator.put(button.getText(), button);
        }
    }

    /**
     * En fonction de l'ActionEvent e reçu en paramètre, agit différemment
     * rv = 1 : Affiche simple d'un tableau des médias (non utilisé)
     * rv = 2 : Affiche avec temps de parole en pourcentage
     * rv = 3 : Suppression d'un média (à corriger)
     * rv = 4 : Affichage général avec les boutons en bas
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        TreeMap<Integer, Media> data = new TreeMap<>();
        TreeMap<Integer, TempsDeParole> dataTDP = new TreeMap<>();
        data = this.model.getMedia();
        if (e.getSource().equals(this.rv.getSearchButton())) {
            switch (this.rv.getSearchButton().getText()) {
                case "Rechercher par année":
                    this.rv.rechercheParAnnee();
                    break;
                case "Rechercher par média":
                    this.rv.rechercheParMedia();
                    break;
                case "Recherche par pourcentage minimal":
                    this.rv.recherchePourcentageMinimalH();
                    break;
                default:
                    this.rv.recherche(this.typeRv);
                    break;
            }
        } else {
            if (e.getSource().equals(this.identificator.get("Afficher tous les médias"))) {
                data = this.model.getMedia();
                this.typeRv = 4;
            }
            if (e.getSource().equals(this.identificator.get("Média avec temps de parole des hommes 2x fois supérieur à celui des femmes"))) {
                dataTDP = this.model.getTDPHomme2FoisSupFemme();
                this.typeRv = 3;
            }
            if (e.getSource().equals(this.identificator.get("TV avec pourcentage temps de parole homme supérieur à X"))) {
                dataTDP = this.getTVPourcentageHommeSupX(0);
                this.typeRv = 5;
            }

            if (e.getActionCommand().equals("ajout")) {
                if (!validate()) {
                    JOptionPane.showMessageDialog(null, "Veuillez réessayer avec un INA de trois caractères, et 0 ou 1 pour Public");
                } else {
                    if (this.model.createMedia(createMedia(true)) == 0) {
                        JOptionPane.showMessageDialog(null, "Veuillez réessayer avec des données valides");
                        return;
                    }
                    FenetreMere fm = (FenetreMere) SwingUtilities.getWindowAncestor((Component) e.getSource());
                    fm.dispose();
                    data = this.model.getMedia();
                }
            }

            if (e.getSource().equals(this.identificator.get("Pourcentage temps de parole"))) {
                dataTDP = this.getPourcentageTDP();
                this.typeRv = 2;
            }
            if (e.getSource().equals(this.identificator.get("Moyenne temps de parole par média et par année"))) {
                dataTDP = this.getMoyenneTDP();
                this.typeRv = 1;
            }
            if (e.getSource().equals(this.identificator.get("Importer à partir d'un csv"))) {
                getDataFromCsv(importFile());
                this.typeRv = 4;
            }
            if (e.getSource().equals(this.identificator.get("Quitter le programme"))) {
                System.exit(0);
            }
            if (e.getSource().equals(this.rv.getDeleteButton())) {
                deleteMedia();
                data = this.model.getMedia();
            }
            if (e.getSource().equals(this.rv.getInsertButton())) {
                data = this.model.getMedia();
                this.openModal("add");
            }
            if (data.isEmpty()) {
                this.rv.modifyPanel(this.typeRv, dataTDP);
            } else {
                this.rv.modifyPanel(this.typeRv, data, null);
            }
        }
    }

    /**
     * permet d'importer un fichier csv
     */
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

    /**
     * permet de rentrer les données dans la base de donné a partir du fichier csv
     */
    private void getDataFromCsv(String path){
        String csvFile = path;
        CSVReader reader = null;

        try {
            if(csvFile == null){
                return;
            }

            String[] str;
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

    /**
     * créé un média
     * @param type
     * @param idIna
     * @param nom
     * @param estPublic
     */
    public Media createMedia(String type, String idIna, String nom, boolean estPublic) {
        Media media = new Media();
        media.setType(type);
        media.setIdIna(idIna);
        media.setNom(nom);
        media.setEstPublic(estPublic);

        return media;
    }

    /**
     * créé un moment
     * @param dateMoment
     * @param jour
     * @param vacances
     * @param estFerie
     * @param heure
     */
    public Moment createMoment(String dateMoment, String jour, String vacances, boolean estFerie, int heure) {
        Moment moment = new Moment();
        moment.setDateMoment(dateMoment);
        moment.setJour(jour);
        moment.setVacances(vacances);
        moment.setEstFerie(estFerie);
        moment.setHeure(heure);

        return moment;
    }

    /**
     * créé un temps de parole
     * @param media
     * @param moment
     * @param tempsFemmes
     * @param tempsHommes
     * @param tempsMusique
     */
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
     * Efface un média
     */
    private void deleteMedia() {
        Object[] options = {"Supprimer", "Annuler"};
        int answer = JOptionPane.showOptionDialog(null, "Êtes-vous sûr de votre choix ?", "Alerte Suppression",
                JOptionPane.YES_OPTION, JOptionPane.NO_OPTION,
                null, options, options[0]);
        if (answer == JOptionPane.YES_OPTION) {
            for (int id : this.rv.getIdRowSelected()) {
                this.model.deleteMedia(id);
            }
        }
    }

    /**
     * Créé un média, vide ou non en fonction du paramètre ajout
     *
     * @param ajout
     * @return
     */
    private Media createMedia(boolean ajout) {
        Media media = new Media();

        media.setType(this.pv.getAllTextFields().get("type").getText());
        media.setIdIna(this.pv.getAllTextFields().get("ina").getText());
        media.setNom(this.pv.getAllTextFields().get("nom").getText());
        media.setEstPublic(this.pv.getAllTextFields().get("public").getText().equals("1"));
        if (!ajout) {
            media.setId(Integer.parseInt((this.pv.getAllTextFields().get("id").getText())));
        }

        return media;
    }

    /**
     * Valide les données entrées dans le cas de la création d'un média
     *
     * @return Boolean
     */
    private boolean validate() {

        if ((this.pv.getAllTextFields().get("ina").getText().length() > 3)) {
            return false;
        }
        if (!StringUtils.isNumeric(this.pv.getAllTextFields().get("public").getText())) {
            return false;
        }
        return Integer.parseInt(this.pv.getAllTextFields().get("public").getText()) == 0 || Integer.parseInt(this.pv.getAllTextFields().get("public").getText()) == 1;
    }

    public void setPv(MediaView pv) {
        this.pv = pv;
    }

    /**
     * Ouvre la fenêtre d'ajout d'un média
     *
     * @param type
     *
     */
    private void openModal(String type) {
        MediaView pv;
        pv = new MediaView();
        new FenetreMere("Ajout d'un média", pv, type);
    }

    public TreeMap<Integer, Media> getMedia() {
        return this.model.getMedia();
    }

    public TreeMap<Integer, Media> getMediaById(int id) {
        return this.model.getMediaById(id);
    }

    public TreeMap<Integer, Media> getMediaByName(String name) {
        return this.model.getMediaByName(name);
    }

    public TreeMap<Integer, TempsDeParole> getPourcentageTDP() {
        return this.model.getPourcentageTDP();
    }

    public TreeMap<Integer, TempsDeParole> getPourcentageTDPByYear(Integer year) {
        return this.model.getPourcentageTDPByYear(year);
    }

    public TreeMap<Integer, TempsDeParole> getMoyenneTDPByMedia(String name) {
        return this.model.getMoyenneTDPByName(name);
    }

    public TreeMap<Integer, TempsDeParole> getMoyenneTDP() {
        return this.model.getMoyenneTDP();
    }

    public TreeMap<Integer, TempsDeParole> getTVPourcentageHommeSupX(Integer percent) {
        return this.model.getTVPourcentageHommeSupX(percent);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
