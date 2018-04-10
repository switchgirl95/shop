/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.Gestionnaire;
import Modele.PersistenceManager;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static shop.Shop.pm;

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
        
        if(isRoot(con_nom.getText(), con_mdp.getText())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_final.xml"));
            StackPane stack = loader.load();
            ((Main_finalController) loader.getController()).setNomAdmin(con_nom.getText());
            Shop.addStack(base, stack);
        }
        
        else {
            try {
                
                gest = pm.getByAttributes(Gestionnaire.class, new PersistenceManager.KeyValue("username",con_nom.getText()),new PersistenceManager.KeyValue("password",con_mdp.getText()));
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        gest.isTypeGest() ? "gestionnaires.fxml" : "cashier1.fxml"));
                        stack = loader.load();
                if (gest.isTypeGest()) ((GestionnairesController) loader.getController()).setGestionnaire(gest);
                else ((Cashier1Controller) loader.getController()).setCassier(gest);
                Shop.addStack(base, stack);
                
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                errorMessage();
            }
        }       
           
    }
    
    private void errorMessage() {
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

        File fXmlFile = new File("../Admin.xml");
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
