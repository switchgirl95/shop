/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.Gestionnaire;
import Modele.PersistenceManager;
import Modele.Produit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author andin
 */
public class GestionnairesController implements Initializable {

    @FXML
    private StackPane stack;
    @FXML
    private HBox catSearch;
    @FXML
    private HBox typesProd;
    @FXML
    private Button test;
    @FXML
    private VBox vbox;
    @FXML
    private HBox addCategory;
    @FXML
    private HBox sortProduit;
    @FXML
    private ToggleGroup sortby;
    @FXML
    private TableView<Produit> table3;
    @FXML
    private AnchorPane blackout;
    @FXML
    private AnchorPane menu;
    @FXML
    private JFXToggleButton active;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton delet;

     private TableColumn code = new TableColumn("Code");
    private TableColumn nom = new TableColumn("Nom");
    private TableColumn codeFournisseur = new TableColumn("Code Fourn");
    private TableColumn quantite = new TableColumn("Quantite");
    private TableColumn price = new TableColumn("Price");
    private TableColumn categoryC = new TableColumn("Categorie");
    /**
     * Initializes the controller class.
     */
    
    Connection connection = null;
    final String PERSISTENCE_UNIT_NAME = "ShopDBPU";
    PersistenceManager pm = null;
    @FXML
    private VBox mendisp;
    @FXML
    private JFXTextField usrnm;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField mdp;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXComboBox<?> type;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                pm = new PersistenceManager(PERSISTENCE_UNIT_NAME);
        //table = tl.products();
        table3.getColumns().addAll(code,nom,codeFournisseur,quantite,price,categoryC);
        List<Produit> all = pm.getAll(Produit.class);
        
        ObservableList<Produit> table = FXCollections.observableArrayList(all);
        //table.addAll(all);
        
        code.setCellValueFactory(new PropertyValueFactory<>("CODEPRODUIT"));
        nom.setCellValueFactory(new PropertyValueFactory<>("NOM"));
        codeFournisseur.setCellValueFactory(new PropertyValueFactory<>("CODEFOURNISSEUR"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("QUANTITE"));
        price.setCellValueFactory(new PropertyValueFactory<>("PRIX"));
        categoryC.setCellValueFactory(new PropertyValueFactory<>("CATEGORIE"));
        
        System.out.println(all.get(0).getNom());
        System.out.println("testons");        
        table3.setItems(table);
        //table.setItems(listProd);
        //addCategory.setVisible(false);
        prepareSlideMenuAnimation();
        pm.stop();
    }    

    @FXML
    private void changeToPdt(MouseEvent event) {
    }

    @FXML
    private void addObj(ActionEvent event) {
        String username = usrnm.getText();
        String nme = name.getText();
        String password = mdp.getText();
        String mail = email.getText();
        String phn = phone.getText();
        String tp = (String) type.getValue();
        boolean tpe = true;
        if(tp.equals("gestionnaire")){
            tpe = false;
        }
        
        boolean actif = true;
        if(active.isSelected()){
            actif = true;
        }
        else
            actif = false;
        Gestionnaire gest = new Gestionnaire(nme,tpe,username,password,actif,phn,mail);
        pm = new PersistenceManager(PERSISTENCE_UNIT_NAME);
        pm.insert(gest);
        pm.stop();
    }

    @FXML
    private void exitMenu(MouseEvent event) {
    }
    
    private void errorMessage(){
       
        String title = "Error!" ; 
        String content = "Wrong username password combination";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        dialogContent.setBody(new Text(content));
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #00bfff;");
        //close.getStyleClass().add("JFXButton");
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
     
     
    private void prepareSlideMenuAnimation() {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), mendisp);
        openNav.setToX(0);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        
        test.setOnAction((ActionEvent evt)->{
                menu.setVisible(true);
                openNav.play();
                
        });
        save.setOnAction((ActionEvent evt)->{
                closeNav.setToX(-(mendisp.getWidth()));
                closeNav.play();
                closeNav.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    menu.setVisible(false);
                }
            });
                
        });
        
    }
    
}
