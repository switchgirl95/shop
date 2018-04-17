/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import Modele.Categorie;
import Modele.Facture;
import Modele.Gestionnaire;
import Modele.ListeFacture;
import Modele.Produit;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static shop.Cashier1Controller.invertDate;
import static shop.Shop.pm;

/**
 *
 * @author andin
 */
public class Catalog {
    List<Produit> prods = pm.getAll(Produit.class);
    //List<Categorie> cats = pm.getAll(Categorie.class);
        public boolean imprimeCatalogue() throws BadElementException, IOException {
        

        /** TODO
          Structure du pdf:
          - logo
          - date heure
          - no facture
          - nom caissière
          - liste produits(codeP, nomP, qté, prixU, prixT)
          - montant total
          - remise
          - net à payer
          - footer
         */

        int i = 0;
        int j = 0;
        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setWidthPercentage(100);
       
    
      
    System.out.println(prods.size());
        
        for(i = 0;i<=prods.size()/4;i++){
                System.out.println("i = "+ i);
            
            for(j=4*i;j<=4*i+3;j++){
                 System.out.println("j = "+j);
                 
                PdfPTable item = new PdfPTable(1);
                item.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                if(j<prods.size()){
                    Produit pdt = prods.get(j);
                    System.out.println(pdt);
                    if(pdt.getPhotos() != null && !pdt.getPhotos().isEmpty()){
                    String imageLink = pdt.getPhotos().get(0).getLien();
                     Image img1 = Image.getInstance(imageLink);
                     PdfPCell celli = new PdfPCell(img1);
                     celli.setFixedHeight(120f);
                     System.out.println(imageLink);
                     celli.setBorder(Rectangle.NO_BORDER);
                     celli.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     item.addCell(celli);
                     
                    }
                    PdfPCell cell = new PdfPCell(new Phrase(pdt.getCodeProduit()+ ""));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    item.addCell(cell);
                    cell = new PdfPCell(new Phrase (pdt.getNom()+ ""));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    item.addCell(cell);
                    cell = new PdfPCell(new Phrase(pdt.getDescriptions()+ ""));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    item.addCell(cell);
                    
//                    cell.setBackgroundColor(new Color(255,255,45));
                    
                }
                else{item.addCell("");}
                table.addCell(item);
            }
            //PdfPCell cell = new PdfPCell(item);
 
        
        
        }
        
       
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);

        // Ecriture du document
        //Document document = new Document(/*new Rectangle(111, 146)*/);
        Document document = new Document(PageSize.A4,10,10,10,10);
        System.out.println(document.getPageSize());
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Factures/test.pdf"));
            document.open();
            //Paragraph header = new Paragraph(noFact + "\n" + date + "\n" + caisse + "\n\n" + miscListe + "\n\n"),
            //          footer = new Paragraph("\n" + total + "\n" + remise + net + "\n\n" + miscLine + "\n" + miscFoot + "\n" + miscLine);

            //document.add(header);
            document.add(table);
            //document.add(footer);
            document.close();
        } catch (Exception e) {
            if (document.isOpen()) document.close();
        }

        return true;
    }


    
}
