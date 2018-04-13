/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.Categorie;
import Modele.GestionStock;
import Modele.Gestionnaire;
import Modele.PersistenceManager;
import Modele.Photo;
import com.jfoenix.controls.*;
import Modele.Produit;
import Modele.photoProdBase;
import java.io.File;
import java.io.FileInputStream;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

    public JFXTextField sId;
    public JFXTextField sNom;
    public JFXTextField sDesc;
    @FXML
    public JFXComboBox<Categorie> sCat;
    public JFXTextField sTCat;
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
    private Text nomAdmin;
    @FXML
    private TableView<Categorie> tableCat;
    @FXML
    private TableView<Produit> tableProd;
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
    @FXML
    private JFXTextField nomCatTxt;
    @FXML
    private JFXButton addCat;
    
    
    List<Produit> prodData;
    List<Categorie> catData;
    List<GestionStock> geStData;
    @FXML
    private HBox pagProd;
    
    int itemsPerPage = 10;
    @FXML
    private StackPane geStTab1;
    @FXML
    private HBox sortGeSt;
    @FXML
    private HBox printGeSt;
    @FXML
    private JFXButton impGeSt;
    @FXML
    private JFXTextField sTGeSt;
    @FXML
    private JFXButton rechercheGeSt;
    @FXML
    private TableView<GestionStock> tableGeSt;
    @FXML
    private Text nomGest;
    
    private Gestionnaire gest;
    @FXML
    private JFXTextField filtIdProd;
    @FXML
    private JFXTextField filtNomProd;
    @FXML
    private JFXTextField filtDescProd;
    @FXML
    private JFXTextField filtCodeFProd;
    
    ObservableList<Produit> tableOP = FXCollections.observableArrayList();
    ObservableList<Categorie> tableOC = FXCollections.observableArrayList();
    @FXML
    private JFXTextField searchCat;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            initTableCat();
            initTableProd();
            initTableGeSt();
            initFilters();
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
        addProd.setVisible(true);
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
        sortGeSt.setVisible(false);
        printGeSt.setVisible(false);
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
        sortGeSt.setVisible(false);
        printGeSt.setVisible(false);
        fillTableCat();
    }
    
    @FXML
    private void changeToGeSt(MouseEvent event) {
        TranslateTransition closeNav = new TranslateTransition(new Duration(250), selectpane);
        closeNav.setToX(340);
        closeNav.play();
        catSearch.setVisible(false);
        addCategory.setVisible(false);
        sortProduit.setVisible(false);
        typesProd.setVisible(false);
        sortGeSt.setVisible(true);
        printGeSt.setVisible(true);
       fillTableGeSt();
    }

    @FXML
    public void logOut(MouseEvent mouseEvent) {
        try {
            Pane login = FXMLLoader.load(getClass().getResource("login.fxml"));
            
            Shop.addStack((Pane)this.stack.getParent(), login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void fillTableProd(){        
        prodData = pm.getAll(Produit.class);
        
        tableOP.addAll(prodData);
        tableProd.setItems(tableOP);
        tableProd.toFront();
        //tableProd.scrollTo(5);
        paginationProd();
    }
    private void fillTableGeSt(){        
        //geStData = pm.getAll(GestionStock.class);
        //ObservableList<GestionStock> table = FXCollections.observableArrayList();
        //table.addAll(geStData);
        //tableGeSt.setItems(table);
        tableGeSt.toFront();
        //tableProd.scrollTo(5);
       // paginationGeSt();
    }

    private void fillTableCat(){        
        catData = pm.getAll(Categorie.class);
        
        tableOC.addAll(catData);
        tableCat.setItems(tableOC);
        tableCat.toFront();
        paginationCat();
        
       
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
    
        public void initTableGeSt(){
        
        TableColumn idGSCol = new TableColumn("Id");
        TableColumn nomGSCol = new TableColumn("Gestionnaire");
        TableColumn qteGSCol = new TableColumn("Quantite");
        TableColumn dateCol = new TableColumn("Date");
        TableColumn typeCol = new TableColumn("Type");
        TableColumn prodGSCol = new TableColumn("Produit");
        tableGeSt.getColumns().addAll(idGSCol,nomGSCol,qteGSCol,dateCol,typeCol,prodGSCol);
        
        idGSCol.setCellValueFactory(new PropertyValueFactory<>("idStock"));
        nomGSCol.setCellValueFactory(new PropertyValueFactory<>("idGest")); 
        qteGSCol.setCellValueFactory(new PropertyValueFactory<>("quantite")); 
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateStock"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("typeGest")); 
        prodGSCol.setCellValueFactory(new PropertyValueFactory<>("idProduit")); 

    
    }
    
    public void initTableProd(){
        TableColumn code = new TableColumn("Code");
        TableColumn nom = new TableColumn("Nom");
        TableColumn codeFournisseur = new TableColumn("Code Fourn");
        TableColumn quantite = new TableColumn("Quantité");
        TableColumn price = new TableColumn("Prix");
        TableColumn categoryC = new TableColumn("Categorie");
        TableColumn desc = new TableColumn("Description");
        TableColumn act = new TableColumn("Status");
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
        photo.setCellValueFactory(new PropertyValueFactory<>("photos"));
        

        photo.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(75);
            imageview.setFitWidth(75);

            //Set up the Table
            TableCell<Produit, List<Photo>> cell = new TableCell<Produit, List<Photo>>() {
                public void updateItem(List<Photo> item, boolean empty) {
                    FileInputStream input=null;
                    if (item != null && item.size() != 0) {
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
        
        act.setCellFactory(param -> {
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

        //
        ObservableList<Categorie> cats = FXCollections.observableArrayList();
        cats.addAll(pm.getAll(Categorie.class));
        sCat.setItems(cats);
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
        loadImages2(prod.getPhotos());
    }

    @FXML
    private void editProd(ActionEvent event) {
        System.out.println("Editing");
        ArrayList<String> list = checkProd();
        
        if (list.isEmpty()){
            rowData.setCategorie(category.getValue());
            rowData.setPrix(Integer.parseInt( prixProd.getText()));
            rowData.setQuantite(Integer.parseInt(qteProd.getText()));
            rowData.setDescriptions(description.getText());
            rowData.setNom(nomProd.getText());
            rowData.setCodeFournisseur(codeFourn.getText());
            rowData.setActif(active.isSelected());
            rowData = pm.insert(rowData);
            notifSuccess("Yep", "Goodgo");
            System.out.println("pr = " + rowData.getCodeProduit());
            //savePhotos(rowData.getCodeProduit());
            fillTableProd();
            exit();
        
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
    
    private void confirmDeleteProd(String title, String content){
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
                dP();
            }
        });
        dialog.show();
    
    }
    
    private void dP(){
        pm.delete(rowData);
        fillTableProd();
        exit();
    }
    
    @FXML
    private void deleteProd(ActionEvent event) {
        confirmDeleteProd("Attention!!","Voulez-vous vraiment supprimer "+ rowData.getNom()+" ?");
        
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
                try {
                    Path dest = Paths.get("../Images/");
                    dest = dest.resolve(((photoProdBase) prodPhoto).getPath().getFileName());
                    Files.copy(((photoProdBase) prodPhoto).getPath(), dest);
                    Photo photo = new Photo(codeProduit, dest.toString());
                    System.out.println(" co = " + photo.getCodeProduit() + ", li = " + photo.getLien());
                    pm.insert(photo);
                } catch (IOException ignored) {
                    System.out.println("echec de copy de " + ((photoProdBase) prodPhoto).getPath());
                }
            }
        }
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

    @FXML
    private void addCat(ActionEvent event) {
        String text = nomCatTxt.getText();
        if (text.isEmpty()) return;
        while (text.charAt(0) == ' ')
            text = text.substring(1);
        if (text.isEmpty()) return;
        Categorie cat = new Categorie(text);
        pm.insert(cat);
        fillTableCat();
    }
    
    private void paginationProd(){
        pagProd.getChildren().clear();
        ArrayList<JFXButton> buttons = new ArrayList<>();
        int noPages = prodData.size()/itemsPerPage + 1;
        
        for (int i=1;i<=noPages;i++){
            final int index = i;
            buttons.add(new JFXButton(Integer.toString(i)));
            buttons.get(i-1).setStyle("-fx-background-color: #00e48f;-fx-text-fill: white;-jfx-button-type: RAISED;");
            buttons.get(i-1).setOnAction((ActionEvent t) -> {
                tableProd.scrollTo(itemsPerPage*(index-1));
            });
        }
        pagProd.getChildren().addAll(buttons);
    
    }
    private void paginationCat(){
        pagProd.getChildren().clear();
        ArrayList<JFXButton> buttons = new ArrayList<>();
        int noPages = catData.size()/itemsPerPage + 1;
        
        for (int i=1;i<=noPages;i++){
            final int index = i;
            buttons.add(new JFXButton(Integer.toString(i)));
            buttons.get(i-1).setStyle("-fx-background-color: #00e48f;-fx-text-fill: white;-jfx-button-type: RAISED;");
            buttons.get(i-1).setOnAction((ActionEvent t) -> {
                tableCat.scrollTo(itemsPerPage*(index-1));
            });
        }
        pagProd.getChildren().addAll(buttons);
    
    }
    
        private void paginationGeSt(){
        pagProd.getChildren().clear();
        ArrayList<JFXButton> buttons = new ArrayList<>();
        int noPages = geStData.size()/itemsPerPage + 1;
        
        for (int i=1;i<=noPages;i++){
            final int index = i;
            buttons.add(new JFXButton(Integer.toString(i)));
            buttons.get(i-1).setStyle("-fx-background-color: #00e48f;-fx-text-fill: white;-jfx-button-type: RAISED;");
            buttons.get(i-1).setOnAction((ActionEvent t) -> {
                tableGeSt.scrollTo(itemsPerPage*(index-1));
            });
        }
        pagProd.getChildren().addAll(buttons);
    
    }

    private void rechercheProd(ActionEvent event) {
        ArrayList<PersistenceManager.KeyValue> kv = new ArrayList<>();
        if (!sId.getText().isEmpty()) kv.add(new PersistenceManager.KeyValue("codeProduit", Integer.parseInt(sId.getText())));
        if (!sNom.getText().isEmpty()) kv.add(new PersistenceManager.KeyValue("nomProduit", sNom.getText()));
        if (!sDesc.getText().isEmpty()) kv.add(new PersistenceManager.KeyValue("descriptions", sDesc.getText()));
        if (sCat.getValue() != null) kv.add(new PersistenceManager.KeyValue("categorie", sCat.getValue()));
        List<Produit> search = new ArrayList<>();
        try {
            search = pm.getAllByAttributes(Produit.class, kv.toArray(new PersistenceManager.KeyValue[]{}));
            tableProd.setItems(FXCollections.observableArrayList(search));
        } catch (Exception e) {

        }
        sId.clear();
        sNom.clear();
        sDesc.clear();
        sCat.getSelectionModel().clearSelection();
        tableProd.toFront();
    }

    private void rechercheCat(ActionEvent event) {
        PersistenceManager.KeyValue kv = null;
        if (!sTCat.getText().isEmpty()) {
            kv = new PersistenceManager.KeyValue("nomCategorie", sTCat.getText());
        }
        List<Categorie> search = null;
        try {
            search = pm.getAllByAttributes(Categorie.class, kv);
            tableCat.setItems(FXCollections.observableArrayList(search));
        } catch (Exception e) {

        }
        sTCat.clear();
        tableCat.toFront();
    }

    @FXML
    private void ImprimerGestStock(ActionEvent event) {
    }

    @FXML
    private void rechercheGeSt(ActionEvent event) {
    }
    
    void setGestionnaire(Gestionnaire gest) {
        this.gest = gest;
        nomGest.setText(gest.getNom());
    }
    private ArrayList<String> checkProd(){
        ArrayList<String> list = new ArrayList<String>();
        if(nomProd.getText().equals("")){list.add("Le produit doit avoir un nom");}
        if(prixProd.getText().equals("")){list.add("Le produit doit avoir un prix");}
        else if (!prixProd.getText().matches("[0-9]+")){list.add("Ce prix est incorrecte");}
        if(prixProd.getText().equals("")){list.add("Le produit doit avoir une quantite");}
        else if (!qteProd.getText().matches("[0-9]+")){list.add("Cette quantite est incorrecte");}
        
        if(codeFourn.getText().equals("")){list.add("Code Fournisseur est absent");}
        
        return list;
    
        
    }
    
    private void initFilters(){
        //ID FILTER
        filtIdProd.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableProd.setItems(tableOP);
            }
            String value = newValue.toLowerCase();
            ObservableList<Produit> subentries = FXCollections.observableArrayList();

            long count = tableProd.getColumns().stream().count();
            for (int i = 0; i < tableProd.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableProd.getColumns().get(0).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableProd.getItems().get(i));
                        break;
                    }
                }
            }
            tableProd.setItems(subentries);
        });
        
        //NAME FILTER
        filtNomProd.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableProd.setItems(tableOP);
            }
            String value = newValue.toLowerCase();
            ObservableList<Produit> subentries = FXCollections.observableArrayList();

            long count = tableProd.getColumns().stream().count();
            for (int i = 0; i < tableProd.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableProd.getColumns().get(1).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableProd.getItems().get(i));
                        break;
                    }
                }
            }
            tableProd.setItems(subentries);
        });
        
        //DESC FILTER
        filtDescProd.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableProd.setItems(tableOP);
            }
            String value = newValue.toLowerCase();
            ObservableList<Produit> subentries = FXCollections.observableArrayList();

            long count = tableProd.getColumns().stream().count();
            for (int i = 0; i < tableProd.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableProd.getColumns().get(2).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableProd.getItems().get(i));
                        break;
                    }
                }
            }
            tableProd.setItems(subentries);
        });
        //codefournisseur FILTER
        filtCodeFProd.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableProd.setItems(tableOP);
            }
            String value = newValue.toLowerCase();
            ObservableList<Produit> subentries = FXCollections.observableArrayList();

            long count = tableProd.getColumns().stream().count();
            for (int i = 0; i < tableProd.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableProd.getColumns().get(4).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableProd.getItems().get(i));
                        break;
                    }
                }
            }
            tableProd.setItems(subentries);
        });
        
        //NOM CAT FILTER
        searchCat.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableCat.setItems(tableOC);
            }
            String value = newValue.toLowerCase();
            ObservableList<Categorie> subentries = FXCollections.observableArrayList();

            long count = tableCat.getColumns().stream().count();
            for (int i = 0; i < tableCat.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableCat.getColumns().get(1).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableCat.getItems().get(i));
                        break;
                    }
                }
            }
            tableCat.setItems(subentries);
        });
        

    
    
    
    }
    
    

    
}
