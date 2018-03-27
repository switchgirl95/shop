/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.Gestionnaire;
import Modele.ListeFacture;
import Modele.PersistenceManager;
import Modele.Produit;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static shop.Shop.pm;

/**
 * FXML Controller class
 *
 * @author andin
 */
public class Cashier1Controller implements Initializable {

    public HBox bar;
    @FXML
    private StackPane stack;
    @FXML
    private ToggleGroup sortby;
    @FXML
    private AnchorPane blackout;
    @FXML
    private VBox mendisp;
    @FXML
    private Button test;
    @FXML
    private AnchorPane menu;
    @FXML
    private TableView<ListeFacture> tableFacture;
    @FXML
    private Text montant;
    @FXML
    private Text caissier;
    @FXML
    private Text date;
    @FXML
    private Text type;
    @FXML
    private TextField remise;
    @FXML
    private TableView<Produit> tableProd;
    @FXML
    private TableColumn tableCode;
    @FXML
    private TableColumn tableNom;
    @FXML
    private TableColumn tableQuantite;

    private List<ListeFacture> produits;
    private Gestionnaire gest;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*produits = new ArrayList<>();

        TableColumn code = new TableColumn("Code");
        TableColumn nom = new TableColumn("Nom");
        TableColumn codeFournisseur = new TableColumn("Code Fourn");
        TableColumn quantite = new TableColumn("Quantité");
        TableColumn price = new TableColumn("Prix");
        TableColumn categoryC = new TableColumn("Categorie");
        TableColumn desc = new TableColumn("Description");
        tableProd.getColumns().addAll(code,nom,codeFournisseur,quantite,price,categoryC,desc);

        code.setCellValueFactory(new PropertyValueFactory<>("codeProduit"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        codeFournisseur.setCellValueFactory(new PropertyValueFactory<>("codeFournisseur"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        price.setCellValueFactory(new PropertyValueFactory<>("prix"));
        categoryC.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        desc.setCellValueFactory(new PropertyValueFactory<>("descriptions"));

        ObservableList<Produit> items = FXCollections.observableArrayList();
        items.addAll(pm.getAll(Produit.class));
        tableProd.setItems(items);

        tableProd.setRowFactory(produitTableView -> {
            TableRow<Produit> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Produit prod = row.getItem();
                    *//*Produit p = null;
                    try {
                        p = pm.get(Produit.class, );
                    } catch (Exception ex) {
                        Logger.getLogger(Main_finalController.class.getName()).log(Level.SEVERE, null, ex);
                    }*//*
                    addToCart(prod, 2);
                }
            });

            return row;
        });

        tableFacture.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("codeProduit"));
        tableFacture.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("prix"));
        tableFacture.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("quantite"));*/

        /*tableFacture.getColumns().addAll(tableCode, tableNom, tableQuantite);
        tableCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        tableNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        tableQuantite.setCellValueFactory(new PropertyValueFactory<>("Quantité"));*/
    }

    private void onShow() {
        caissier.setText(gest.getUsername());
        date.setText(invertDate(parseCalendar(Calendar.getInstance())));
    }

    @FXML
    private void exitMenu(MouseEvent event) {
        System.out.println(Double.toString(stack.getHeight()));
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        closeNav.setToY(stack.getHeight());
         AnchorPane.setBottomAnchor(mendisp,null);
            closeNav.play();
            closeNav.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                menu.setVisible(false);
            }
        });
    }

    @FXML
    private void openMenu(ActionEvent event) {
        System.out.println(Double.toString(stack.getHeight()-mendisp.getHeight()));
        TranslateTransition openNav=new TranslateTransition(new Duration(350), mendisp);
        openNav.setToY(0);
       // TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        menu.setVisible(true);
                openNav.play();
                openNav.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AnchorPane.setBottomAnchor(mendisp,0.0);
            }
                });
        
    }

    private void addToCart(Produit produit, int quantite) {
        ListeFacture lf = new ListeFacture(produit.getCodeProduit(), 0, quantite, produit.getPrix());
        produits.add(lf);
        fillTableFact();
    }

    private void fillTableFact() {
        ObservableList<ListeFacture> table = FXCollections.observableArrayList();
        table.addAll(produits);
        tableFacture.setItems(table);
    }

    private double calculeMontant() {
        double montant = 0;
        for (ListeFacture lf : produits)
            montant += lf.getPrix() * lf.getQuantite();

        return montant;
    }

    void setCassier(Gestionnaire gest) {
        this.gest = gest;
        onShow();
    }

    public static String parseCalendar(Calendar c){
        String add1 = "", add2 = "";
        if (c.get(Calendar.MONTH) + 1 < 10)
            add1 = "0";
        if (c.get(Calendar.DAY_OF_MONTH) < 10)
            add2 = "0";
        return c.get(Calendar.YEAR) + "-" + add1 + (c.get(Calendar.MONTH) + 1) + "-" + add2 + c.get(Calendar.DAY_OF_MONTH);
    }

    public static String invertDate(String s) {
        if (s.charAt(2) == '-') // format DD-MM-YYYY
            return s.substring(6, 10) + "-" + s.substring(3, 5) + "-" + s.substring(0, 2);
        else // format YYYY-MM-DD
            return s.substring(8, 10) + "-" + s.substring(5, 7) + "-" + s.substring(0, 4);
    }
    
}
