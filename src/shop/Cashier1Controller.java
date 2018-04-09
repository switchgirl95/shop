/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.Facture;
import Modele.Gestionnaire;
import Modele.ListeFacture;
import Modele.Produit;
import com.jfoenix.controls.JFXButton;
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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;

import javafx.scene.layout.HBox;
import org.controlsfx.control.Notifications;

import static shop.Shop.pm;

/**
 * FXML Controller class
 *
 * @author andin
 */
public class Cashier1Controller implements Initializable {

    @FXML
    private JFXTextField total;
    @FXML
    private StackPane stack;
    @FXML
    private AnchorPane blackout;
    @FXML
    private VBox mendisp;
    @FXML
    private Button test;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panier = new HashSet<>();
        initMenu();
        initTables();
        remise.textProperty().addListener((observable, oldValue, newValue) -> updateMontantView());
    }

    private void initTables() {
        tablePCode.setCellValueFactory(new PropertyValueFactory<>("codeProduit"));
        tablePNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tablePQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tablePPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        ObservableList<Produit> tableP = FXCollections.observableArrayList();
        tableP.addAll(pm.getAll(Produit.class));
        tableProduits.setItems(tableP);
        tableProduits.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Produit>() {
            @Override
            public void changed(ObservableValue<? extends Produit> observableValue, Produit oldP, Produit newP) {
                // affiche le produit dans la zone du haut
                if (newP != null) {
                    idProd.setText(newP.getCodeProduit() + "");
                    nomProd.setText(newP.getNom());
                    qteProd.setText("");
                    qteProd.requestFocus();
                }
            }
        });

        tableFCode.setCellValueFactory(new PropertyValueFactory<>("codeProduit"));
        tableFNom.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
        tableFQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tableFPrixU.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tableFPrixT.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        //tableFPrix.setCellValueFactory(new PropertyValueFactory<>("PRIX"));
        
        //TableColumn<Person, Person> tableFSupp = new TableColumn<>("Anti-social");
        //tableFSupp.setMinWidth(40);
        //tableFSupp.setMinWidth(40);
        tableFSupp.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableFSupp.setCellFactory(param -> new TableCell<ListeFacture, ListeFacture>() {
            private final Button deleteButton = new Button("Unfriend");

            @Override
            protected void updateItem(ListeFacture fac, boolean empty) {
                super.updateItem(fac, empty);

                if (fac == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                
                deleteButton.setOnAction(event -> table.remove(fac));
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
        } catch (NumberFormatException e) {
            // erreur
            System.out.println("Quantité pas sérieuse");
        }
    }

    @FXML
    public void saveFacture(ActionEvent event) {
        // On sauvegarde les items
        Facture f = new Facture(gest, invertDate(parseCalendar(Calendar.getInstance())), Double.parseDouble(remise.getText()), Double.parseDouble(remise.getText()), true);
        f = pm.insert(f);
        for (ListeFacture lf : panier) {
            lf.setIdFacture(f.getIdFacture());
            pm.insert(lf);
        }
        // TODO: Diminuer les stocks des produits achetés

        // et on imprime la facture
        if (imprimeFacture(f));
        else ;
    }

    private void initMenu(){
        TranslateTransition closeNav=new TranslateTransition(new Duration(0), mendisp);
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

    private void fillTableF() {
        table.addAll(panier);
        tableFacture.setItems(table);
    }
    
    private void onShow() {
        caissier.setText(gest.getUsername());
        date.setText(invertDate(parseCalendar(Calendar.getInstance())));
    }

    private void updateMontantView() {
        montant2.setText(calculeMontant() + "");
        try {
            Double t = calculeMontant() - Double.parseDouble(remise.getText());
            total.setText(t > 0 ? t + "" : "0.0");
        } catch (NumberFormatException ingnored) {}
    }

    private double calculeMontant() {
        double montant = 0;
        for (ListeFacture lf : panier)
            montant += lf.getPrix() * lf.getQuantite();

        return montant;
    }

    private boolean imprimeFacture(Facture facture) {
        /*Gestionnaire gest = facture.getGestionnaire();
        List<ListeFacture> listeFactures = facture.getListeFacture();
        for (ListeFacture lf : listeFactures)
            lf.getProduit();*/

        return true;
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

    @FXML
    private void exitMenu(ActionEvent event) {
                System.out.println(Double.toString(stack.getHeight()));
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        closeNav.setToY(stack.getHeight());
         AnchorPane.setBottomAnchor(mendisp,null);
            closeNav.play();
            closeNav.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                menu.setVisible(false);
                blackout.setVisible(false);
            }
        });
    }
    private void notifSuccess(String title, String content){
         Notifications notificationBuilder = Notifications.create()
                        .title(title)
                        .text(content)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("click");
                        }
                        });
                
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
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent __) {
                dialog.close();
            }
        });
        dialog.show();
    }
}

