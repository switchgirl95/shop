/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author andin
 */
public class ProdPhotoController implements Initializable  {
    public String link;
    @FXML
    private ImageView iView;
    private AnchorPane anchor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
FileInputStream input = null;
        try {
            input = new FileInputStream("images.png");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProdPhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        anchor.getChildren().add(imageView);
    }    

    @FXML
    private void deleteImage(ActionEvent event) {
    }
    
}
