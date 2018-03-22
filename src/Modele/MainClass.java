package Modele;

import Modele.Categorie;
import Modele.PersistenceManager;

import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by arnold on 06/03/18.
 */
public class MainClass {
    public static void main(String[] args) {
        Connection connection = null;
        final String PERSISTENCE_UNIT_NAME = "ShopDBPU";
        PersistenceManager pm = null;

        try {
            Facture f = new Facture(1, 2, "2017-03-03", 0, 500, true);
            //Gestionnaire g = new Gestionnaire(2, "Antoine", true, "antoine", "password", true, "691898298", "antoine@yahoo.fr");
           
            pm = new PersistenceManager(PERSISTENCE_UNIT_NAME);
            pm.insert(f);
            Facture c = pm.get(Facture.class, 1);
            c.setDateFacture("2017-03-03");
            
            pm.insert(c);
            System.out.println(c.getDateFacture());
            pm.stop();

        } catch (Exception e) {
            e.printStackTrace();
            if(pm != null) pm.stop();
        }
    }
}
