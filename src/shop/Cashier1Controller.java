/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.Facture;
import Modele.GestionStock;
import Modele.Gestionnaire;
import Modele.ListeFacture;
import Modele.PersistenceManager;
import Modele.Photo;
import Modele.Produit;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import org.controlsfx.control.Notifications;

import static shop.Shop.pm;

/**
 * FXML Controller class
 *
 * @author andin
 */
public class Cashier1Controller implements Initializable {

    @FXML
    public Text nomCaissier;
    @FXML
    private JFXTextField total;
    @FXML
    private StackPane stack;
    @FXML
    private AnchorPane blackout;
    @FXML
    private VBox mendisp;
    @FXML
    private AnchorPane menu;
    @FXML
    private TableView<Produit> tableProduits;
    @FXML
    private TableColumn tablePCode;
    @FXML
    private TableColumn tablePNom;
    @FXML
    private TableColumn tablePQuantite;
    @FXML
    private TableColumn tablePPrix;
    @FXML
    private TableView<ListeFacture> tableFacture;
    @FXML
    private Text montant;
    private Text caissier;
    private Text date;
    @FXML
    private TextField remise;
    @FXML
    private TableColumn tableFCode;
    @FXML
    private TableColumn tableFNom;
    @FXML
    private TableColumn tableFQuantite;
    @FXML
    private TableColumn tableFPrixU;
    @FXML
    private TableColumn tableFPrixT;
    @FXML
    private JFXTextField idProd;
    @FXML
    private JFXTextField nomProd;
    @FXML
    private JFXTextField qteProd;

    private HashSet<ListeFacture> panier;
    private Gestionnaire gest;
    @FXML
    private HBox HBProd;
    @FXML
    private JFXTextField montant2;
    @FXML
    private JFXButton closeMenu;
    @FXML
    private JFXButton sauvegarder;
    @FXML
    private JFXButton annuler;
    @FXML
    private TableColumn<ListeFacture, ListeFacture> tableFSupp;
    ObservableList<ListeFacture> table = FXCollections.observableArrayList();
    ObservableList<Produit> tableP = FXCollections.observableArrayList();
    ObservableList<Facture> tableH = FXCollections.observableArrayList();
    @FXML
    private JFXTextField filtIdProd;
    @FXML
    private JFXTextField filtNomProd;
    @FXML
    private JFXButton test1;
    @FXML
    private TableColumn photo;
    @FXML
    private HBox pagProd;
    int itemsPerPage = 10;
    @FXML
    private StackPane tabFactures;
    @FXML
    private StackPane tabHistorique;
    @FXML
    private TableView<Facture> tableHist;
    @FXML
    private TableColumn tableHId;
    @FXML
    private TableColumn tableHGest;
    @FXML
    private TableColumn tableHDate;
    @FXML
    private TableColumn tableHRemise;
    @FXML
    private TableColumn tableHMontant;
    @FXML
    private TableColumn tableHType;
    @FXML
    private Pane selectpane;
    @FXML
    private HBox HBHist;
    @FXML
    private HBox sortFacture;
    @FXML
    private HBox sortHist;
    
    JFXDatePicker beforeDate = new JFXDatePicker();
    JFXDatePicker afterDate = new JFXDatePicker();
    SimpleDateFormat formatter1 =new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");  
     SimpleDateFormat formatter2 =new SimpleDateFormat("dd/MM/yyyy");  
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panier = new HashSet<>();
        initMenu();
        initTables();
        initFilter();
        setFactories();
        paginationProd(tableP);
        remise.textProperty().addListener((observable, oldValue, newValue) -> updateMontantView());
        sortHist.getChildren().addAll(beforeDate,afterDate);
        sortHist.setVisible(false);
    }

    private void initTables() {
        //----------Table Produits -------------------//
        tablePCode.setCellValueFactory(new PropertyValueFactory<>("codeProduit"));
        tablePNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tablePQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tablePPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        fillTableP();
        tableProduits.getSelectionModel().selectedItemProperty().addListener((observableValue, oldP, newP) -> {
            // affiche le produit dans la zone du haut
            if (newP != null) {
                idProd.setText(newP.getCodeProduit() + "");
                nomProd.setText(newP.getNom());
                qteProd.setText("");
                qteProd.requestFocus();
            }
        });

        
        //------------------Table Facture ----------------------//
        tableFCode.setCellValueFactory(new PropertyValueFactory<>("codeProduit"));
        tableFNom.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
        tableFQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tableFPrixU.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tableFPrixT.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));

        //TableColumn<Person, Person> tableFSupp = new TableColumn<>("Anti-social");
        //tableFSupp.setMinWidth(40);
        //tableFSupp.setMinWidth(40);
        tableFSupp.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableFSupp.setCellFactory(param -> new TableCell<ListeFacture, ListeFacture>() {
            private JFXButton deleteButton = new JFXButton("x");

            @Override
            protected void updateItem(ListeFacture fac, boolean empty) {
                super.updateItem(fac, empty);

                if (fac == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                
                deleteButton.setStyle("-fx-background-color: red;-fx-text-fill: white;-jfx-button-type: RAISED;");
                deleteButton.setOnAction(event -> {table.remove(fac); panier.remove(fac);});

                deleteButton.setOnAction(event -> {
                    panier.remove(fac);
                    table.remove(fac);
                });

            }
        });
        photo.setCellValueFactory(new PropertyValueFactory<>("photos"));
        
    // ------------------Table Historique --------------------//
        
        tableHId.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
        tableHGest.setCellValueFactory(new PropertyValueFactory<>("gestionnaire"));
        tableHDate.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        tableHRemise.setCellValueFactory(new PropertyValueFactory<>("remise"));
        tableHMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        tableHType.setCellValueFactory(new PropertyValueFactory<>("typeFact"));
        
    }

    @FXML
    private void openMenu(ActionEvent event) {
        System.out.println(Double.toString(stack.getHeight()-mendisp.getHeight()));
        TranslateTransition openNav=new TranslateTransition(new Duration(350), mendisp);
        openNav.setToY(0);
       // TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        menu.setVisible(true);
        blackout.setVisible(true);
        openNav.play();
        openNav.setOnFinished(event1 -> AnchorPane.setBottomAnchor(mendisp,0.0));
        fillTableF();
        updateMontantView();
    }

    @FXML
    private void addToCart(ActionEvent event) {
        int qte = 0;
        if (idProd.getText().isEmpty()) return;
        try {
            qte = Integer.parseInt(qteProd.getText());
            if (qte == 0) throw new NumberFormatException();
            ListeFacture lf = new ListeFacture(pm.get(Produit.class, Integer.parseInt(idProd.getText())), 0, qte);

            Produit p = pm.get(Produit.class, Integer.parseInt(idProd.getText()));
            if(Integer.parseInt(qteProd.getText()) <= p.getQuantite()) {
                if (!panier.add(lf)) {
                    panier.remove(lf);
                    panier.add(lf);
                }

                // on revient à l'état initial
                idProd.setText("");
                nomProd.setText("");
                qteProd.setText("");
                tableProduits.getSelectionModel().select(null);
                // message de confirmation d'ajout
                System.out.println("Ajouté");

           } else errorMessage("Erreur","Il n'y a pas assez de ce produit!");

        } catch (NumberFormatException e) {
            // erreur
            System.out.println("Quantité pas sérieuse");
            errorMessage("Erreur", "Ce n'est pas un entier");
        }
    }

    @FXML
    public void saveFacture(ActionEvent event) {
        // On sauvegarde les items

        Facture f = new Facture(gest, parseCalendar(Calendar.getInstance()), Double.parseDouble(remise.getText()), Double.parseDouble(montant2.getText()), true);
        f = pm.insert(f);
        pm.em().refresh(f);
        for (ListeFacture lf : panier) {
            lf.setIdFacture(f.getIdFacture());
            pm.insert(lf);

            // On sauvegarde la transaction
            GestionStock gs = new GestionStock(gest, lf.getQuantite(), parseCalendar(Calendar.getInstance()), gest.isTypeGest(), lf.getProduit());
            pm.insert(gs);
            
            // On diminue les quantités
            lf.getProduit().setQuantite(lf.getProduit().getQuantite() - lf.getQuantite());
            pm.insert(lf.getProduit());
        }

        // et on imprime la facture
        if (imprimeFacture(f)) {
            exitMenu(null);
            notifSuccess("Information", "La facture a bien été imprimée");
        } else errorMessage("Erreur", "Echec d'impression de la facture");
        panier.clear();
        fillTableP();
    }

    private void initMenu(){
        TranslateTransition closeNav=new TranslateTransition(new Duration(0), mendisp);
        closeNav.setToY(stack.getHeight());
        AnchorPane.setBottomAnchor(mendisp,null);
        closeNav.play();
        closeNav.setOnFinished(event -> menu.setVisible(false));
    }

    private void fillTableP() {
        tableP.clear();
        tableP.addAll(pm.getAll(Produit.class));
        tableProduits.setItems(tableP);
        tableProduits.toFront();
    }

    private void fillTableF() {
        table.clear();
        table.addAll(panier);
        tableFacture.setItems(table);
    }
    
    private void fillTableH() {
        tableH.clear();
        tableH.addAll(pm.getAll(Facture.class));
        tableHist.setItems(tableH);
    }

    private void updateMontantView() {
        montant2.setText(calculeMontant() + "");
        try {
            Double t = calculeMontant() - calculeMontant() * Double.parseDouble(remise.getText()) / 100;
            total.setText(t > 0 ? t + "" : "0.0");
        } catch (NumberFormatException ingnored) {}
    }

    private double calculeMontant() {
        double montant = 0;
        for (ListeFacture lf : panier)
            montant += lf.getPrix() * lf.getQuantite();

        return montant;
    }

    private void initFilter(){
        //date filter
        
        
        afterDate.setOnAction(new EventHandler<ActionEvent>(){
          @Override
	 public void handle(ActionEvent event) 
	 {
             fillTableH();
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                 ObservableList<Facture> subentries = FXCollections.observableArrayList();
		 LocalDate date = afterDate.getValue();
		 long count = tableHist.getColumns().stream().count();
            for (int i = 0; i < tableHist.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableHist.getColumns().get(2).getCellData(i);
                    LocalDate nowDate =  LocalDate.parse(entry.substring(0, entry.length() - 2), formatter);
                    if (!beforeDate.getValue().isAfter(nowDate) && !afterDate.getValue().isBefore(nowDate)) 
                    {
                        subentries.add(tableHist.getItems().get(i));
                        break;
                    }
                }
            }
            tableHist.setItems(subentries);
            setFactories();
	 }
        });

        
        
        
        //search ID
        filtIdProd.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableProduits.setItems(tableP);
            }
            String value = newValue.toLowerCase();
            ObservableList<Produit> subentries = FXCollections.observableArrayList();

            long count = tableProduits.getColumns().stream().count();
            for (int i = 0; i < tableProduits.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableProduits.getColumns().get(0).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableProduits.getItems().get(i));
                        break;
                    }
                }
            }
            tableProduits.setItems(subentries);
        });

        //Search Name

        filtNomProd.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableProduits.setItems(tableP);
                paginationProd(tableP);
                setFactories();
            }
            String value = newValue.toLowerCase();
            ObservableList<Produit> subentries = FXCollections.observableArrayList();

            long count = tableProduits.getColumns().stream().count();
            for (int i = 0; i < tableProduits.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableProduits.getColumns().get(1).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableProduits.getItems().get(i));
                        break;
                    }
                }
            }
            tableProduits.setItems(subentries);
            paginationProd(subentries);
            setFactories();
        });


   }
    
    private void paginationProd(ObservableList prodData){
        pagProd.getChildren().clear();
        ArrayList<JFXButton> buttons = new ArrayList<>();
        int noPages = prodData.size()/itemsPerPage + 1;

        for (int i=1;i<=noPages;i++){
            final int index = i;
            buttons.add(new JFXButton(Integer.toString(i)));
            buttons.get(i-1).setStyle("-fx-background-color: #00e48f;-fx-text-fill: white;-jfx-button-type: RAISED;");
            buttons.get(i-1).setOnAction((ActionEvent t) -> {
                tableProduits.scrollTo(itemsPerPage*(index-1));
            });
        }
        pagProd.getChildren().addAll(buttons);

    }

    private boolean imprimeFacture(Facture facture) {
        Gestionnaire gest = facture.getGestionnaire();

        List<ListeFacture> listeFactures = facture.getListeFacture();


        /** TODO
          Structure du pdf:
          - logo
          - date heure
          - no facture
          - nom caissière
          - liste produits(codeP, nomP, qté, prixU, prixT)
          - montant total
          - remise
          - net à payer
          - footer
         */
        File logo = new File("le logo ici");
        String date = "Date: " + invertDate(facture.getDateFacture());
        String noFact = "Facture no." + facture.getIdFacture();
        String caisse = "Caissier(ère): " + gest.getNom();
        String total = "Montant total: " + montant2.getText() + " XAF";
        double r = Double.parseDouble(this.remise.getText());
        String remise = r > 0 ? "Remise: " + r + "%\n" : "";
        String net = "Net à payer: " + this.total.getText() + " XAF";
        String miscListe = "****************** Liste des produits *******************";
        String miscFoot = "Les marchandises vendues et livrées ne sont ni reprises, ni échangées.";
        String miscLine = "---------------------------------------------------------------------";

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell("Code P.");
        //table.addCell("nom Produit");
        table.addCell("quantité");
        table.addCell("Prix U");
        table.addCell("Prix T");
        for (ListeFacture lf : facture.getListeFacture()) {
            table.addCell(lf.getCodeProduit() + "");
            //table.addCell(lf.getNomProduit());
            table.addCell(lf.getQuantite() + "");
            table.addCell(lf.getPrix() + "");
            table.addCell(lf.getPrixTotal() + "");
        }
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        // Ecriture du document
        //Document document = new Document(/*new Rectangle(111, 146)*/);
        Document document = new Document(PageSize.A6,10,10,10,10);
        System.out.println(document.getPageSize());
        //PrinterJob pj = PrinterJob.createPrinterJob(Printer.getDefaultPrinter());
        try {
            PdfWriter.getInstance(document, new FileOutputStream("../Factures/Facture" + facture.getIdFacture() + ".pdf"));
            document.open();
            Paragraph header = new Paragraph(noFact + "\n" + date + "\n" + caisse + "\n\n" + miscListe + "\n\n"),
                      footer = new Paragraph("\n" + total + "\n" + remise + net + "\n\n" + miscLine + "\n" + miscFoot + "\n" + miscLine);

            document.add(header);
            document.add(table);
            document.add(footer);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (document.isOpen()) document.close();
            return false;
        }

        return true;
    }

    void setCassier(Gestionnaire gest) {
        this.gest = gest;
        nomCaissier.setText(gest.getNom());
    }

    public static String parseCalendar(Calendar c){
        String add1 = "", add2 = "";
        if (c.get(Calendar.MONTH) + 1 < 10)
            add1 = "0";
        if (c.get(Calendar.DAY_OF_MONTH) < 10)
            add2 = "0";
        return c.get(Calendar.YEAR) + "-" + add1 + (c.get(Calendar.MONTH) + 1) + "-" + add2 + c.get(Calendar.DAY_OF_MONTH) + " " +
                c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
    }

    public static String invertDate(String s) {
        String[] ss = s.split(" ");
        if (ss[0].charAt(2) == '-') // format DD-MM-YYYY
            return ss[0].substring(6, 10) + "-" + ss[0].substring(3, 5) + "-" + ss[0].substring(0, 2) + " " + ss[1];
        else // format YYYY-MM-DD
            return ss[0].substring(8, 10) + "-" + ss[0].substring(5, 7) + "-" + ss[0].substring(0, 4) + " " + ss[1];
    }

    @FXML
    private void exitMenu(ActionEvent event) {
                System.out.println(Double.toString(stack.getHeight()));
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        closeNav.setToY(stack.getHeight());
         AnchorPane.setBottomAnchor(mendisp,null);
            closeNav.play();
            closeNav.setOnFinished(event1 -> {
                menu.setVisible(false);
                blackout.setVisible(false);
            });
    }

    @FXML
    public void logOut(MouseEvent mouseEvent) {
        gest = null;
        try {
            Pane login = FXMLLoader.load(getClass().getResource("login.fxml"));
            Shop.addStack((Pane) this.stack.getParent(), login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifSuccess(String title, String content){
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(content)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT)
                .darkStyle()
                .onAction(event -> System.out.println("click"));
                
        notificationBuilder.show();
                    
    }

    private void errorMessage(String title, String content){

        //String title = "Error!" ; 
        //String content = "Wrong username password combination";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        dialogContent.setBody(new Text(content));
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #00bfff;");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(stack, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        close.setOnAction((ActionEvent __) -> {
            dialog.close();
        });
       dialog.show();
    }

    @FXML
    private void setFactories(){
        
        tableHRemise.setCellFactory(param -> {
            Text status = new Text();
            //Set up the Table
            TableCell<Facture, Double> cell = new TableCell<Facture, Double>() {
                public void updateItem(Double item, boolean empty) {
                    if (item != null)
                        status.setText(item + "%");
                    else{status.setText("");}
                }
                
            };
            // Attach the imageview to the cell
            cell.setGraphic(status);
            return cell;
        });
        
        
        photo.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(100);
            imageview.setFitWidth(100);

            //Set up the Table
            TableCell<Produit, List<Photo>> cell = new TableCell<Produit, List<Photo>>() {
                @Override
                public void updateItem(List<Photo> item, boolean empty) {
                    FileInputStream input=null;
                    if (item != null && !item.isEmpty()) {
                        System.out.print(item.get(0).getCodeProduit());
                        System.out.println(item.get(0).getLien());
                        try {
                            input = new FileInputStream(item.get(0).getLien());
                            Image image = new Image(input);
                            imageview.setImage(image);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Main_finalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
             };
             // Attach the imageview to the cell
             cell.setGraphic(imageview);
             return cell;
        });
        
        
        
    
    
    }

    @FXML
    private void changeToFact(MouseEvent event) {
        TranslateTransition closeNav = new TranslateTransition(new Duration(250), selectpane);
        closeNav.setToX(0);
        closeNav.play();
        HBProd.toFront();
        tableHist.setVisible(false);
        tableProduits.setVisible(true);
        //tableFacture.toFront();
        HBProd.setVisible(true);
        HBHist.setVisible(false);
        sortHist.setVisible(false);
        //tableFacture.setVisible(true);
        tableHist.setVisible(false);
        sortFacture.setVisible(true);
        fillTableF();
    }

    @FXML
    private void changeToHIst(MouseEvent event) {
        TranslateTransition closeNav = new TranslateTransition(new Duration(250), selectpane);
        closeNav.setToX(170);
        closeNav.play();
        tableHist.toFront();
        tableHist.setVisible(!false);
        tableProduits.setVisible(!true);
        HBHist.toFront();
        sortHist.toFront();
        //tableFacture.setVisible(!true);
        //tableHist.setVisible(!false);
        HBProd.setVisible(!true);
        HBHist.setVisible(!false);
        sortHist.setVisible(!false);
        sortFacture.setVisible(false);
        fillTableH();
    }

    @FXML
    private void imprimerHistorique(ActionEvent event) {
        tableHist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Facture>() {
            @Override
            public void changed(ObservableValue<? extends Facture> observable, Facture oldValue, Facture newValue) {
                if (newValue != null)
                    imprimeFacture(newValue);
            }
        });
    }
}

