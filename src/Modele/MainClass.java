package Modele;

import Modele.Categorie;
import Modele.PersistenceManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by arnold on 06/03/18.
 */
public class MainClass {
    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        Connection connection = null;
        final String PERSISTENCE_UNIT_NAME = "ShopDBPU";
        PersistenceManager pm = null;

        // step 1
        Document document = new Document();
// step 2
        PdfWriter.getInstance(document, new FileOutputStream("essai.pdf"));
// step 3
        document.open();
// step 4
        document.add(new Paragraph("Hello World!"));
// step 5
        document.close();

        /*try {
            //Facture f = new Facture(1, 2, "2017-03-03", 0, 500, true);
            //Gestionnaire g = new Gestionnaire(2, "Antoine", true, "antoine", "password", true, "691898298", "antoine@yahoo.fr");
           
            pm = new PersistenceManager(PERSISTENCE_UNIT_NAME);

            Categorie c = pm.get(Categorie.class, 200000);
            Produit p = new Produit(c, 250, 50, "all", "nom", "Auchan", true);
            p = pm.insert(p);
            System.out.println(p.getCodeProduit());

            pm.stop();
        } catch (Exception e) {
            e.printStackTrace();
            if(pm != null) pm.stop();
        }*/
    }
}
