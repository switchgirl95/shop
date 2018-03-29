package Modele;

import com.jfoenix.controls.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class photoProdBase extends StackPane {

    private StackPane stackPane;
    private ImageView iView;
    private AnchorPane anchorPane;
    private JFXButton button;
    private String lien;

    public photoProdBase(File f) throws FileNotFoundException {
        lien = f.getAbsolutePath();
        setUp();
        
    //cm.getItems().add(removeRec);
    //createContextMenuEvent(cm, rec);

}
     public photoProdBase(Photo f) throws FileNotFoundException {
        lien = f.getLien();
        setUp();
    //cm.getItems().add(removeRec);
    //createContextMenuEvent(cm, rec);

}
    private void setUp() throws FileNotFoundException{
    FileInputStream input = new FileInputStream(lien);
        Image image = new Image(input);
        //ImageView imageView = new ImageView(image);
        stackPane = new StackPane();
        iView = new ImageView(image);
        anchorPane = new AnchorPane();
        button = new JFXButton("x");

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(180.0);
        setPrefWidth(180.0);
        setStyle("-fx-background-color: purple;");

        stackPane.setPrefHeight(169.0);
        stackPane.setPrefWidth(148.0);

        iView.setFitHeight(169.0);
        iView.setFitWidth(168.0);
        iView.setPickOnBounds(true);
        iView.setPreserveRatio(true);

        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(200.0);

        AnchorPane.setRightAnchor(button, -10.0);
        AnchorPane.setTopAnchor(button, -10.0);
        button.setLayoutX(156.0);
        button.setLayoutY(1.0);
        button.setStyle("-fx-background-color: red;-fx-text-fill: white;-jfx-button-type: RAISED;");
        button.setFont(new Font(20.0));
        
        
        
        stackPane.getChildren().add(iView);
        getChildren().add(stackPane);
        anchorPane.getChildren().add(button);
        getChildren().add(anchorPane);
        
        button.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent t) {

            ((VBox) photoProdBase.this.getParent()).getChildren().remove(photoProdBase.this);

        }
    });
        

    
    }
    public void setLien(String l){
        this.lien = l;
    }
    public String getLien(){
        return this.lien;
    }

    
}
