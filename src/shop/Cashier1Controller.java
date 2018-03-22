/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author andin
 */
public class Cashier1Controller implements Initializable {

    @FXML
    private StackPane stack;
    @FXML
    private ToggleGroup sortby;
    @FXML
    private AnchorPane blackout;
    @FXML
    private VBox mendisp;
    @FXML
    private Button test;
    @FXML
    private AnchorPane menu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // System.out.println(Double.toString(stack.getHeight() - mendisp.getHeight()));
       // prepareSlideMenuAnimation();
    }   
  /*   private void prepareSlideMenuAnimation() {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), mendisp);
        openNav.setToY(400);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        
        test.setOnAction((ActionEvent evt)->{
                menu.setVisible(true);
                openNav.play();
                
        });
       menu.setOnAction((ActionEvent evt)->{
                closeNav.setToY(-(mendisp.getHeight()));
                closeNav.play();
                closeNav.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    menu.setVisible(false);
                }
            });
                
        });
        
    }*/

    @FXML
    private void exitMenu(MouseEvent event) {
        System.out.println(Double.toString(stack.getHeight()));
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        closeNav.setToY(stack.getHeight());
            closeNav.play();
            closeNav.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                menu.setVisible(false);
            }
        });
    }

    @FXML
    private void openMenu(ActionEvent event) {
        System.out.println(Double.toString(stack.getHeight()-mendisp.getHeight()));
        TranslateTransition openNav=new TranslateTransition(new Duration(350), mendisp);
        openNav.setToY(stack.getHeight()-mendisp.getHeight());
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), mendisp);
        
        test.setOnAction((ActionEvent evt)->{
                menu.setVisible(true);
                openNav.play();
                
        });
    }
    
}
