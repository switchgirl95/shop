/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;


import Modele.Gestionnaire;
import Modele.Photo;
import Modele.Produit;
import com.jfoenix.controls.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.controlsfx.control.Notifications;

import static shop.Shop.pm;

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
    private TableView<Gestionnaire> table3;
    @FXML
    private AnchorPane blackout;
    @FXML
    private AnchorPane menu;
    @FXML
    private JFXToggleButton active;
    private JFXButton save;

    @FXML
    private JFXTextField id; 
    private final TableColumn idCol = new TableColumn("Id");
    private final TableColumn nom = new TableColumn("Nom");
    private final TableColumn typeCol = new TableColumn("Type Compte");
    private final TableColumn uname = new TableColumn("Username");
    private final TableColumn actif = new TableColumn("Status");
    private final TableColumn tel= new TableColumn("Telephone");
    private final TableColumn mail = new TableColumn("Email");
    
    int itemsPerPage = 20;
    /**
     * Initializes the controller class.
     */

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

    private Gestionnaire gest;
    @FXML
    private HBox ModifyProd;
    @FXML
    private JFXButton exitMenu;
    @FXML
    private JFXButton editGest;
    @FXML
    private JFXButton deleteGest;
    @FXML
    private JFXButton addGest;
    
    List<Gestionnaire> gestData;
    @FXML
    private ToggleGroup typeG;
    @FXML
    private JFXRadioButton type1;
    @FXML
    private JFXRadioButton type2;
    @FXML
    private HBox pagGest;
    
    private int rowIndex;
    
    private Gestionnaire rowData;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableGest();
        fillTableGest();
        prepareSlideMenuAnimation();
    }    

    @FXML
    private void changeToPdt(MouseEvent event) {
    }

    

    @FXML
    private void exitMenu(MouseEvent event) {
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
     
     
    private void prepareSlideMenuAnimation() {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), mendisp);
        openNav.setToX(0);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        
        test.setOnAction((ActionEvent evt)->{
                initChamp();
                blackout.setVisible(true);
                menu.setVisible(true);
                addGest.setVisible(true);
                editGest.setVisible(false);
                deleteGest.setVisible(false);
                openNav.play();
                
        });
        exitMenu.setOnAction((ActionEvent evt)->{
               exit();
                
        });
        
        initChamp();
    }
    
    private void initChamp(){
        id.setText("#");
        usrnm.setText("");
        name.setText("");
        mdp.setText("");
        email.setText("");
        phone.setText("");
        
        
    
    
    }

    void setGestionnaire(Gestionnaire gest) {
        this.gest = gest;
    }

    
        private void exit(){
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        closeNav.setToX(-(mendisp.getWidth()));
            closeNav.play();
            closeNav.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                blackout.setVisible(false);
                menu.setVisible(false);
            }
        });
    }
    
    private void initTableGest(){
    
    table3.getColumns().addAll(idCol, nom, typeCol,uname,actif,tel,mail);

        idCol.setCellValueFactory(new PropertyValueFactory<>("idGest"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("typeGest"));
        uname.setCellValueFactory(new PropertyValueFactory<>("username"));
        actif.setCellValueFactory(new PropertyValueFactory<>("actif"));
        tel.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        mail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        actif.setCellFactory(param -> {
            Text status = new Text();
            //Set up the Table
            TableCell<Gestionnaire, Boolean> cell = new TableCell<Gestionnaire, Boolean>() {
                public void updateItem(Boolean item, boolean empty) {
                    
                    if (item != null) {
                       if (item){ 
                        status.setText("Actif");
                        status.setFill(Color.GREEN);
                       }
                       else{
                           status.setText("Passif");
                        status.setFill(Color.RED);
                       }
                    }
                    
                    
                }
             };
             // Attach the imageview to the cell
             cell.setGraphic(status);
             return cell;
        });
        
        typeCol.setCellFactory(param -> {
            Text status = new Text();
            //Set up the Table
            TableCell<Gestionnaire, Boolean> cell = new TableCell<Gestionnaire, Boolean>() {
                public void updateItem(Boolean item, boolean empty) {
                    
                    if (item != null) {
                       if (item){ 
                        status.setText("Gestionnaire");
                       }
                       else{
                           status.setText("Cassiere");
                       }
                    }
                    
                    
                }
             };
             // Attach the imageview to the cell
             cell.setGraphic(status);
             return cell;
        });
        
        
        
            table3.setRowFactory(tv -> {
            TableRow<Gestionnaire> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Gestionnaire gest = row.getItem();
                    try {
                        rowData = pm.get(Gestionnaire.class, gest.getIdGest());
                    } catch (Exception ex) {
                        Logger.getLogger(Main_finalController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    rowIndex = row.getIndex();
                    
                    try {
                        fillProdEdit(rowData);
                    } catch (Exception ex) {
                        Logger.getLogger(Main_finalController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Double click on: "+rowData.getNom());
                    TranslateTransition openNav=new TranslateTransition(new Duration(350), mendisp);
                    blackout.setVisible(true);
                    menu.setVisible(true);
                    addGest.setVisible(!true);
                    editGest.setVisible(!false);
                    deleteGest.setVisible(!false);
                    openNav.setToX(0);
                    openNav.play();
                }
            });
            return row ;
        });
        
        fillTableGest();
    }
    private void fillTableGest(){
        gestData = pm.getAll(Gestionnaire.class);
        ObservableList<Gestionnaire> table = FXCollections.observableArrayList();
        table.addAll(gestData);
        table3.setItems(table);
        paginationGest();
    
    }
    
    private void paginationGest(){
         pagGest.getChildren().clear();
        ArrayList<JFXButton> buttons = new ArrayList<>();
        int noPages = gestData.size()/itemsPerPage + 1;
        
        for (int i=1;i<=noPages;i++){
            final int index = i;
            buttons.add(new JFXButton(Integer.toString(i)));
            buttons.get(i-1).setStyle("-fx-background-color: #00e48f;-fx-text-fill: white;-jfx-button-type: RAISED;");
            buttons.get(i-1).setOnAction((ActionEvent t) -> {
                table3.scrollTo(itemsPerPage*(index-1));
            });
        }
        pagGest.getChildren().addAll(buttons);
    
    
    }
    
    public void fillProdEdit(Gestionnaire gest) throws Exception{
        initChamp();
        id.setText(Integer.toString(gest.getIdGest()));
        name.setText(gest.getNom());
        mdp.setText(gest.getPassword());
        email.setText(gest.getEmail());
        phone.setText(gest.getTelephone());
        active.setSelected(gest.isActif() ? true : false);
        if (gest.isTypeGest()){
            type1.setSelected(true);
        }
        else{type2.setSelected(true);}
        
    }

    
    @FXML
    private void editGest(ActionEvent event) {
        
    }

    @FXML
    private void deleteGest(ActionEvent event) {
    }

    @FXML
    private void addGest(ActionEvent event) {
    
        String usn = usrnm.getText();
        String nm = name.getText();
        String mp =mdp.getText();
        String eml = email.getText();
        String ph = phone.getText();
        
        Gestionnaire gest = new Gestionnaire(nm,type1.isSelected(),usn,mp,active.isSelected(),ph,eml);
        pm.insert(gest);
        fillTableGest();
        exit();
    }
}
