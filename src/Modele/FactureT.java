/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author andin
 */
public class FactureT {
        private final SimpleStringProperty codeProduit;
        private final SimpleStringProperty nomProduit;
        private final SimpleIntegerProperty quantite;
        private final SimpleDoubleProperty prix;
        private final SimpleDoubleProperty prixTotal;

        private FactureT(String cprod, String nprod, int qte, double prx,double prxt) {
            this.codeProduit = new SimpleStringProperty(cprod);
            this.nomProduit = new SimpleStringProperty(nprod);
            this.quantite = new SimpleIntegerProperty(qte);
            this.prix = new SimpleDoubleProperty(prx);
            this.prixTotal = new SimpleDoubleProperty(prxt);
            
        }

        public String getCodeProduit() {
            return codeProduit.get();
        }

        public void setCodeProduit(String fName) {
            codeProduit.set(fName);
        }

        public String getNomProduit() {
            return nomProduit.get();
        }

        public void setNomProduit(String fName) {
            nomProduit.set(fName);
        }
        
        public int getQuantite() {
            return quantite.get();
        }

        public void setQuantite(int qte) {
            quantite.set(qte);
        }
        
        public double getPrix() {
            return prix.get();
        }

        public void setPrix(double prx) {
            prix.set(prx);
        }
        
        public double getPrixTotal() {
            return prixTotal.get();
        }

        public void setPrixTotal(double prxt) {
            prix.set(prxt);
        }
}

