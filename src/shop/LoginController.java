/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.Gestionnaire;
import Modele.PersistenceManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author andin
 */
public class LoginController implements Initializable {

    @FXML
    private VBox connexion;
    @FXML
    private JFXTextField con_nom;
    @FXML
    private JFXPasswordField con_mdp;
    @FXML
    private JFXButton buttoncon;
    @FXML
    private StackPane stack;
    @FXML
    private AnchorPane base;
    
     Connection connection = null;
        final String PERSISTENCE_UNIT_NAME = "ShopDBPU";
        PersistenceManager pm = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    

    @FXML
    private void checkId(ActionEvent event) throws IOException {
        Gestionnaire gest = null;
        pm = new PersistenceManager(PERSISTENCE_UNIT_NAME);
        
        
        if(isRoot(con_nom.getText(), con_mdp.getText())){
            base.getChildren().clear();
        StackPane stack = FXMLLoader.load(getClass().getResource("main_final.fxml"));
         AnchorPane.setTopAnchor(stack, 0.0);
       AnchorPane.setLeftAnchor(stack, 0.0);
       AnchorPane.setRightAnchor(stack, 0.0);
       AnchorPane.setBottomAnchor(stack, 0.0);
        base.getChildren().add(stack);
        pm.stop();
        }
        
        else{
            try {
                gest = pm.getByAttributes(Gestionnaire.class, new PersistenceManager.KeyValue("USERNAME",con_nom.getText()),new PersistenceManager.KeyValue("PASSWORD",con_mdp.getText()));
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(gest != null){
                base.getChildren().clear();
                StackPane stack = FXMLLoader.load(getClass().getResource("main_final.fxml"));
                AnchorPane.setTopAnchor(stack, 0.0);
                AnchorPane.setLeftAnchor(stack, 0.0);
                AnchorPane.setRightAnchor(stack, 0.0);
                AnchorPane.setBottomAnchor(stack, 0.0);
                base.getChildren().add(stack);
            }
            else{
        errorMessage();
        
            }
        }       
           
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
    
    boolean isRoot(String username, String password){
    
    try {

	File fXmlFile = new File("xmlPrueba.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	
	doc.getDocumentElement().normalize();

	String usernameR = doc.getElementsByTagName("username").item(0).getTextContent();
        String passwordR = doc.getElementsByTagName("password").item(0).getTextContent();
        return (usernameR.equals(username)&&passwordR.equals(password));

    } catch (Exception e) {
	e.printStackTrace();
    }
        return false;
    }
            
}
