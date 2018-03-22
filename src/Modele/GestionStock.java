package Modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity (name = "GESTIONSTOCK")
public class GestionStock {
    @Id
    @Column(name = "IDSTOCK") private int idStock;
    @Column(name = "IDGEST") private int idGest;
    @Column(name = "QUANTITE") private int quantite;
    @Column(name = "DATESTOCK") private String dateStock;
    @Column(name = "TYPEGEST") private boolean typeGest;
    @Column(name = "IDPRODUIT") private int idProduit;

    //Constructeurs
    //**************************************
    public GestionStock() {
        this.idGest =0;
        this.idStock =0;
    }

    public GestionStock(int idGest, int quantite, String dateStock, boolean typeGest, int idProduit) {
        this.idGest = idGest;
        this.quantite = quantite;
        this.dateStock = dateStock;
        this.typeGest = typeGest;
        this.idProduit = idProduit;
    }

    public GestionStock(int idStock, int idGest, int quantite, String dateStock, boolean typeGest, int idProduit) {
        this(idGest, quantite, dateStock, typeGest, idProduit);
        this.idStock = idStock;
    }

    //Getters et Setters
    //****************************************
    public int getIdStock() {
        return idStock;
    }

    public int getIdGest() {
        return idGest;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getDateStock() {
        return dateStock;
    }

    public boolean isTypeGest() {
        return typeGest;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setDateStock(String dateStock) {
        this.dateStock = dateStock;
    }

    public void setTypeGest(boolean typeGest) {
        this.typeGest = typeGest;
    }
}
