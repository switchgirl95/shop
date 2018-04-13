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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
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

    public Text nomGest;
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
    private final TableColumn typeCol = new TableColumn("Type Gestionnaire");
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
    @FXML
    private StackPane stackP;
    @FXML
    private Text menuTitle;
    @FXML
    private Text nomAdmin;
    @FXML
    private StackPane mendisp1;
    
    ObservableList<Gestionnaire> table;
    @FXML
    private JFXTextField filtIdGest;
    @FXML
    private JFXTextField filtNomGest;
    @FXML
    private JFXTextField filtUnameGest;
    @FXML
    private JFXTextField filtTelGest;
    @FXML
    private JFXTextField filtEmailGest;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableGest();
        fillTableGest();
        initFilters();
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
        JFXDialog dialog = new JFXDialog(mendisp1, dialogContent, JFXDialog.DialogTransition.BOTTOM);
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
        TranslateTransition openNav=new TranslateTransition(new Duration(350), mendisp1);
        openNav.setToX(0);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp1);
        
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
        menuTitle.setText("Nouveau Gestionnaire");
        id.setText("#");
        usrnm.setText("");
        name.setText("");
        mdp.setText("");
        email.setText("");
        phone.setText("");
    }

    public void setNomAdmin(String nom) {
        nomAdmin.setText("Bienvenue " + nom);
    }

    
    private void exit(){
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp1);
        closeNav.setToX(-(mendisp1.getWidth()));
        closeNav.play();
        closeNav.setOnFinished(event -> {
            blackout.setVisible(false);
            menu.setVisible(false);
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
                           status.setText("Cassier");
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
                    TranslateTransition openNav=new TranslateTransition(new Duration(350), mendisp1);
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
        table = FXCollections.observableArrayList();
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
        menuTitle.setText("Gestionnaire:  "+ gest.getIdGest());
        id.setText(Integer.toString(gest.getIdGest()));
        usrnm.setText(gest.getUsername());
        name.setText(gest.getNom());
        mdp.setText(gest.getPassword());
        email.setText(gest.getEmail());
        phone.setText(gest.getTelephone());
        active.setSelected(gest.isActif() ? true : false);
        if (gest.isTypeGest()) type1.setSelected(true); else type2.setSelected(true);
    }

    
    @FXML
    private void editGest(ActionEvent event) {
        ArrayList<String> list = checkGest();
        if (list.isEmpty()){
        rowData.setUsername(usrnm.getText());
        rowData.setNom(name.getText()); 
        rowData.setPassword(mdp.getText());
        rowData.setEmail(email.getText());
        rowData.setTelephone(phone.getText());
        rowData.setTypeGest(type1.isSelected());
        rowData.setActif(active.isSelected());
try{
        //Gestionnaire gest = new Gestionnaire(Integer.parseInt(id.getText()), nm,type1.isSelected(),usn,mp,active.isSelected(),ph,eml);
        pm.insert(rowData);
        table.add(gest);
        //fillTableGest();
        exit();
        }catch(Exception e){System.out.println(e);} 
        }     

        else{
            String message = "";
            int i = 0;
            for(String s : list){
                message = message + "Error " + Integer.toString(++i)+": " + s +"\n";
                
            }
            errorMessage("Echec!!!", message);
        
        }
    }
    
    private void confirmDeleteGest(String title, String content){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        dialogContent.setBody(new Text(content));
        JFXButton close = new JFXButton("Confirmer");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #00bfff;");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(mendisp1, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent __) {
                dg();
            }
        });
        dialog.show();
    
    }
    private void dg(){
        pm.delete(rowData);
        //table.remove(rowData);
        fillTableGest();
        exit();
        notifSuccess("Success!!", "Gestionnaire supprime avec success.");
    
    }

    @FXML
    private void deleteGest(ActionEvent event) {
        confirmDeleteGest("Attention!!","Voulez-vous vraiment supprimer "+ rowData.getNom()+" ?");
    }

    @FXML
    private void addGest(ActionEvent event) {
        String usn = usrnm.getText();
        String nm = name.getText();
        String mp = mdp.getText();
        String eml = email.getText();
        String ph = phone.getText();
        
        Gestionnaire gest = new Gestionnaire(nm,type1.isSelected(),usn,mp,active.isSelected(),ph,eml);
        
        pm.insert(gest);
        table.add(gest);
        //fillTableGest();
        exit();
    }



    @FXML
    private void logOut(ActionEvent event) {
        System.out.println("tt");
       // gest = null;
        try {
            Pane login = FXMLLoader.load(getClass().getResource("login.fxml"));
            Shop.addStack((Pane) this.stackP.getParent(), login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ArrayList<String> checkGest(){
        System.out.println("Checking");
        ArrayList<String> list = new ArrayList<String>();
        if(usrnm.getText().equals("")){list.add("Nom d'utilisateur absent");}
        if(name.getText().equals("")){list.add("Nom absent");}
        if (mdp.getText().matches("[0-9]+")){list.add("Mot de passe absent");}
        if (mdp.getText().matches("[0-9]+")){list.add("Adresse mail absent");}
        //else if (!mdp.getText().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")){list.add("L'adresse mail est incorrecte");}
        if(phone.getText().equals("")){list.add("Numero de telephone absent");}
        else if (!phone.getText().matches("[0-9]+")){list.add("Ce numero est incorrecte");}
       
        return list;
        
    }
    private void initFilters(){
        //ID FILTER
        filtIdGest.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table3.setItems(table);
            }
            String value = newValue.toLowerCase();
            ObservableList<Gestionnaire> subentries = FXCollections.observableArrayList();

            long count = table3.getColumns().stream().count();
            for (int i = 0; i < table3.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table3.getColumns().get(0).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table3.getItems().get(i));
                        break;
                    }
                }
            }
            table3.setItems(subentries);
        });
        
        //NAME FILTER
        filtNomGest.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table3.setItems(table);
            }
            String value = newValue.toLowerCase();
            ObservableList<Gestionnaire> subentries = FXCollections.observableArrayList();

            long count = table3.getColumns().stream().count();
            for (int i = 0; i < table3.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table3.getColumns().get(1).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table3.getItems().get(i));
                        break;
                    }
                }
            }
            table3.setItems(subentries);
        });
        
        //DESC FILTER
        filtUnameGest.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table3.setItems(table);
            }
            String value = newValue.toLowerCase();
            ObservableList<Gestionnaire> subentries = FXCollections.observableArrayList();

            long count = table3.getColumns().stream().count();
            for (int i = 0; i < table3.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table3.getColumns().get(3).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table3.getItems().get(i));
                        break;
                    }
                }
            }
            table3.setItems(subentries);
        });
        
        //TELEPHONE FILTER
        filtTelGest.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table3.setItems(table);
            }
            String value = newValue.toLowerCase();
            ObservableList<Gestionnaire> subentries = FXCollections.observableArrayList();

            long count = table3.getColumns().stream().count();
            for (int i = 0; i < table3.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table3.getColumns().get(5).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table3.getItems().get(i));
                        break;
                    }
                }
            }
            table3.setItems(subentries);
        });
        
        //Email FILTER
        filtEmailGest.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table3.setItems(table);
            }
            String value = newValue.toLowerCase();
            ObservableList<Gestionnaire> subentries = FXCollections.observableArrayList();

            long count = table3.getColumns().stream().count();
            for (int i = 0; i < table3.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table3.getColumns().get(6).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table3.getItems().get(i));
                        break;
                    }
                }
            }
            table3.setItems(subentries);
        });
    
    }
}
