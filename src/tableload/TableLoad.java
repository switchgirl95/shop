/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableload;

import Modele.PersistenceManager;
import Modele.Produit;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author andin
 */
public class TableLoad {
    Connection connection = null;
        final String PERSISTENCE_UNIT_NAME = "ShopDBPU";
        PersistenceManager pm = new PersistenceManager(PERSISTENCE_UNIT_NAME);
    public TableView products(){
        
        TableView<Produit> table = new TableView<Produit>();
        TableColumn codePro = null; 
        TableColumn cat = null;
        TableColumn prix = null;
        TableColumn qte = null;
        TableColumn desc = null;
        TableColumn nom = null;
        TableColumn codeFourn = null;
        TableColumn actif = null;
        
        codePro.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("Code Produit"));
        cat.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("Categorie"));
        prix.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("Prix"));
        qte.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("Quantite"));
        desc.setCellValueFactory(new PropertyValueFactory<Produit, String>("Description"));
        nom.setCellValueFactory(new PropertyValueFactory<Produit, String>("Nom"));
        codeFourn.setCellValueFactory(new PropertyValueFactory<Produit, String>("Code Fournisseur"));
        actif.setCellValueFactory(new PropertyValueFactory<Produit, Boolean>("Actif"));
        ObservableList<Produit> listProd = FXCollections.observableArrayList(pm.getAll(Produit.class));
        table.setItems(listProd);
        return table;
    }
    
}
