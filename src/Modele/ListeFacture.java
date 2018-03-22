package Modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity(name = "LISTE_FACTURE")
@IdClass(ListeFacture.Key.class)
public class ListeFacture
{
    @Id
    @Column(name = "CODEPRODUIT") private int codeProduit;
    @Id
    @Column(name = "IDFACTURE") private int idFacture;
    @Column(name = "QUANTITE") private int quantite;
    @Column(name = "PRIX") private double prix;

    //Constructeurs
    //**********************************
    public ListeFacture() {
        this.codeProduit =0;
        this.idFacture =0;
    }

    public ListeFacture(int codeProduit, int idFacture, int quantite, double prix) {
        this.codeProduit = codeProduit;
        this.idFacture = idFacture;
        this.quantite = quantite;
        this.prix = prix;
    }

    //Getters et Setters
    //***********************************
    public int getCodeProduit() {
        return codeProduit;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    protected class Key implements Serializable {
        // used to define the LISTE_FACTURE table composite key
        int codeProduit;
        int idFacture;

        public Key() {}

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Key && codeProduit == ((Key) obj).codeProduit && idFacture == ((Key) obj).idFacture;
        }

        @Override
        public int hashCode() {
            return 31 * codeProduit + idFacture;
        }
    }
}
