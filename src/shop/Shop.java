/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.PersistenceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author andin
 */
public class Shop extends Application {
    public static final String PERSISTENCE_UNIT_NAME = "ShopDBPU";
    public static PersistenceManager pm = null;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            pm = new PersistenceManager(PERSISTENCE_UNIT_NAME);
            launch(args);
            pm.stop();
        } catch (Exception e) {
            if (pm != null) pm.stop();
        }
    }

    public static void addStack(Pane base, Pane stack) {
        base.getChildren().clear();
        AnchorPane.setTopAnchor(stack, 0.0);
        AnchorPane.setLeftAnchor(stack, 0.0);
        AnchorPane.setRightAnchor(stack, 0.0);
        AnchorPane.setBottomAnchor(stack, 0.0);
        base.getChildren().add(stack);
    }
    
}
