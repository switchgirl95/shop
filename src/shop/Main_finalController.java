/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.Categorie;
import Modele.PersistenceManager;
import Modele.Photo;
import com.jfoenix.controls.*;
import Modele.Produit;
import Modele.photoProdBase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import tableload.TableLoad;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import net.bytebuddy.matcher.FilterableList;
import org.controlsfx.control.Notifications;

import static shop.Shop.pm;

/**
 * FXML Controller class
 *
 * @author andin
 */
public class Main_finalController implements Initializable {

    @FXML
    private Button test;
    @FXML
    private AnchorPane blackout;
    @FXML
    private AnchorPane menu;
    @FXML
    private StackPane stack;
    private ScrollPane mendisp;
    @FXML
    private JFXTextField prixProd;
    @FXML
    private JFXTextField qteProd;
    @FXML
    private JFXTextField codeFourn;
    @FXML
    private JFXComboBox<Categorie> category;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXToggleButton active;
    @FXML
    private JFXTextField nomProd;
    @FXML
    private StackPane pdtTab;
    @FXML
    private StackPane catTab;
    @FXML
    private ToggleGroup sortby;
    @FXML
    private HBox addCategory;
    @FXML
    private HBox sortProduit;
    @FXML
    private VBox vbox;
    @FXML
    private HBox catSearch;
    @FXML
    private HBox typesProd;
    @FXML
    private Pane selectpane;
    @FXML
    private Text pdtText;
    @FXML
    private Text catText;
    @FXML
    private Text nompage;
    @FXML
    private Text greeting;
    @FXML
    private Text signOut;
    @FXML
    private TableView<Categorie> tableCat;
    @FXML
    private TableView<Produit> tableProd;
    @FXML
    private StackPane catTab1;
    @FXML
    private Text catText1;
    @FXML
    private JFXTextField idProd;
    
    Connection connection = null;
    public int rowIndex;
    @FXML
    private HBox ModifyProd;
    @FXML
    private JFXButton editProd;
    @FXML
    private JFXButton deleteProd;
    @FXML
    private JFXButton addProd;
    private Produit rowData;
    @FXML
    private VBox photoList;
    
    Parent root = null;
    @FXML
    private JFXButton addPhoto;
    @FXML
    private StackPane mendisp1;
    @FXML
    private JFXButton exitMenu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            initTableCat();
            initTableProd();
            addCategory.setVisible(false);
            prepareSlideMenuAnimation();
            fillTableProd();
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
                addPhoto.setVisible(true);
                editProd.setVisible(false);
                deleteProd.setVisible(false);
                openNav.play();
                
        });
        // On initialise tous les champs
        initChamp();
        
    }
    
    private void initChamp(){
    
        idProd.setText("#");
        nomProd.setText("");
        prixProd.setText("");
        qteProd.setText("");
        codeFourn.setText("");
        description.setText("");
        photoList.getChildren().clear();
        ObservableList<Categorie> cats = FXCollections.observableArrayList();
        cats.addAll(pm.getAll(Categorie.class));
        category.setItems(cats);
    }
    
    

    @FXML
    private void changeToPdt(MouseEvent event) {
        TranslateTransition closeNav = new TranslateTransition(new Duration(250), selectpane);
        closeNav.setToX(0);
        closeNav.play();
        addCategory.setVisible(false);
        catSearch.setVisible(false);
        sortProduit.setVisible(true);
        typesProd.setVisible(true);
        fillTableProd();
        exit();       
    }

    @FXML
    private void changeToCat(MouseEvent event) {
        TranslateTransition closeNav = new TranslateTransition(new Duration(250), selectpane);
        closeNav.setToX(170);
        closeNav.play();
        catSearch.setVisible(true);
        addCategory.setVisible(true);
        sortProduit.setVisible(false);
        typesProd.setVisible(false);
        fillTableCat();
    }

    @FXML
    private void signOut(MouseEvent event) {
    }
    
    private void fillTableProd(){        
        List<Produit> all = pm.getAll(Produit.class);
        ObservableList<Produit> table = FXCollections.observableArrayList();
        table.addAll(all);
        tableProd.setItems(table);
        tableProd.toFront();
    }

    private void fillTableCat(){        
        List<Categorie> all = pm.getAll(Categorie.class);
        ObservableList<Categorie> table = FXCollections.observableArrayList();
        table.addAll(all);
        tableCat.setItems(table);
        tableCat.toFront();
       
    }
    
    public void initTableCat(){
        
        TableColumn id = new TableColumn("Id");
        TableColumn nom = new TableColumn("Nom");
        tableCat.getColumns().addAll(id,nom);
        
        id.setCellValueFactory(new PropertyValueFactory<>("idcategorie"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nomCategorie"));     
        tableProd.setRowFactory(tv -> {
            TableRow<Produit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Produit rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getNom());
                }
            });
            return row ;
        });
    
    }
    
    public void initTableProd(){
        TableColumn code = new TableColumn("Code");
        TableColumn nom = new TableColumn("Nom");
        TableColumn codeFournisseur = new TableColumn("Code Fourn");
        TableColumn quantite = new TableColumn("Quantité");
        TableColumn price = new TableColumn("Prix");
        TableColumn categoryC = new TableColumn("Categorie");
        TableColumn desc = new TableColumn("Description");
        TableColumn act = new TableColumn("Actif?");
        TableColumn photo = new TableColumn("Photo");
        tableProd.getColumns().addAll(code,nom,codeFournisseur,quantite,price,categoryC,desc,act,photo);
        
        code.setCellValueFactory(new PropertyValueFactory<>("codeProduit"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        codeFournisseur.setCellValueFactory(new PropertyValueFactory<>("codeFournisseur"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        price.setCellValueFactory(new PropertyValueFactory<>("prix"));
        categoryC.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        desc.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
        act.setCellValueFactory(new PropertyValueFactory<>("actif"));

        
        
        tableProd.setRowFactory(tv -> {
            TableRow<Produit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Produit prod = row.getItem();
                    try {
                        rowData = pm.get(Produit.class, prod.getCodeProduit());
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
                    addProd.setVisible(!true);
                    editProd.setVisible(!false);
                    deleteProd.setVisible(!false);
                    openNav.setToX(0);
                    openNav.play();
                }
            });
            return row ;
        });

    }
    
    public void fillProdEdit(Produit prod) throws Exception{
        initChamp();
        idProd.setText(Integer.toString(prod.getCodeProduit()));
        nomProd.setText(prod.getNom());
        prixProd.setText(Integer.toString(prod.getPrix()));
        qteProd.setText(Integer.toString(prod.getQuantite()));
        codeFourn.setText(prod.getCodeFournisseur());
        description.setText(prod.getDescriptions());
        active.setSelected(prod.isActif());
        category.getSelectionModel().select(prod.getCategorie());
        
        List<Photo> photos = pm.getAll(Photo.class);
        loadImages2(photos);
    }
   

    @FXML
    private void editProd(ActionEvent event) {
        System.out.println("Editing");
        rowData.setCategorie(category.getValue());
        rowData.setPrix(Integer.parseInt( prixProd.getText()));
        rowData.setQuantite(Integer.parseInt(qteProd.getText()));
        rowData.setDescriptions(description.getText());
        rowData.setNom(nomProd.getText());
        rowData.setCodeFournisseur(codeFourn.getText());
        rowData.setActif(active.isSelected());
        rowData = pm.insert(rowData);
        System.out.println("pr = " + rowData.getCodeProduit());
        savePhotos(rowData.getCodeProduit());
        fillTableProd();
        exit();
    }

    @FXML
    private void deleteProd(ActionEvent event) {
        pm.delete(rowData);
        fillTableProd();
        exit();
    }

    @FXML
    private void addProd(ActionEvent event) {
        Categorie cat = category.getValue();
        int ppd =Integer.parseInt(prixProd.getText());
        int qpd = Integer.parseInt(qteProd.getText());
        String des = description.getText();
        String npd = nomProd.getText();
        String cfo = codeFourn.getText();
        System.out.println("??");
        Produit prod = new Produit(cat, ppd, qpd, des, npd, cfo, active.isSelected());//new Produit(cat,ppd,qpd,des,npd,cfo,actif);
        prod = pm.insert(prod);
        System.out.println("pr = " + prod.getCodeProduit());
        savePhotos(prod.getCodeProduit());

        fillTableProd();
        exit();
    }

    private void savePhotos(int codeProduit) {
        List<Node> prodPhotos = photoList.getChildren();
        for(Node prodPhoto : prodPhotos) {
            if (prodPhoto instanceof photoProdBase) {
                Photo photo = new Photo(codeProduit,((photoProdBase) prodPhoto).getLien());
                System.out.println(" co = " + photo.getCodeProduit() + ", li = " + photo.getLien());
                pm.insert(photo);
            }
        }
    }
    
    private void exit(){
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp1);
        closeNav.setToX(-(mendisp1.getWidth()));
            closeNav.play();
            closeNav.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                blackout.setVisible(false);
                menu.setVisible(false);
            }
        });
    }
    
    private void loadImages1(List<File> files) throws FileNotFoundException{
        for (File f : files){
        StackPane file = new photoProdBase(f);
        photoList.getChildren().add(file);
        }
        
        //photoList.getChildren().add(rori);
        //photoList.getChildren().addAll(rori,rori2);
    
    }
    
    private void loadImages2(List<Photo> photos) throws FileNotFoundException{
        for (Photo f : photos){
        StackPane file = new photoProdBase(f);
        photoList.getChildren().add(file);
        }
    }

    @FXML
    private void addPhoto(ActionEvent event) throws FileNotFoundException {
        Window theStage = menu.getParent().getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image files");
        //fileChooser.setInitialDirectory(new File("X:\\testdir\\two"));
        fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("image files", "*.jpg","*.png"));

    List<File> selectedFiles = fileChooser.showOpenMultipleDialog(theStage);

if (selectedFiles != null) {
    
    loadImages1(selectedFiles);
    }

else {
    //actionStatus.setText("PDF file selection cancelled.");
}
    }

    @FXML
    private void exitMenu(ActionEvent event) {
        exit();
    }
}
