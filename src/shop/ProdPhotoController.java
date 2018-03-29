/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author andin
 */
public class ProdPhotoController implements Initializable {

    @FXML
    private ImageView iView;
    @FXML
    private JFXButton button;
    @FXML
    private StackPane photoPane;
    
    public VBox photoList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Parent photoList = photoPane.getParent();
  
    }    

    @FXML
    private void deleteImage(ActionEvent event) {
       //photoPane.getParent().getChildren().remove();
    }
    
}
