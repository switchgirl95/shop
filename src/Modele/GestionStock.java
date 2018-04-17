package Modele;

import javax.persistence.*;

@Entity (name = "GESTIONSTOCK")
public class GestionStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSTOCK") private int idStock;
    @ManyToOne
    @JoinColumn(name="IDGEST", nullable=false)
    private Gestionnaire gestionnaire;
    @Column(name = "QUANTITE") private int quantite;
    @Column(name = "DATESTOCK") private String dateStock;
    @Column(name = "TYPEGEST") private boolean typeGest;
    @ManyToOne
    @JoinColumn(name="CODEPRODUIT", nullable=false)
    private Produit produit;

    //Constructeurs
    //**************************************
    public GestionStock() {
        this.idStock =0;
    }

    public GestionStock(Gestionnaire gest, int quantite, String dateStock, boolean typeGest, Produit produit) {
        this.gestionnaire = gest;
        this.quantite = quantite;
        this.dateStock = dateStock;
        this.typeGest = typeGest;
        this.produit = produit;
    }

    public GestionStock(int idStock, Gestionnaire gest, int quantite, String dateStock, boolean typeGest, Produit produit) {
        this(gest, quantite, dateStock, typeGest, produit);
        this.idStock = idStock;
    }

    //Getters et Setters
    //****************************************
    public int getIdStock() {
        return idStock;
    }

    public Gestionnaire getGestionnaire() {
        return gestionnaire;
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

    public Produit getProduit() {
        return produit;
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
