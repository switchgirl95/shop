package Modele;


import javax.persistence.*;
import java.util.List;
import java.util.Random;
import static shop.Shop.pm;

@Entity(name = "PRODUIT")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODEPRODUIT") private int codeProduit;
    @ManyToOne
    @JoinColumn(name="IDCATEGORIE", nullable=false)
    private Categorie categorie;
    @Column(name = "PRIX") private int prix;
    @Column(name = "QUANTITE") private int quantite;
    @Column(name = "DESCRIPTIONS") private String descriptions;
    @Column(name = "NOM") private String nom;
    @Column(name = "CODEFOURNISSEUR") private String codeFournisseur;
    @Column(name = "ACTIF") private boolean actif;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CODEPRODUIT")
    private List<Photo> photos;

    //Constructeurs
    //************************************

    public Produit() {
        this.codeProduit = 0;
    }

    public Produit(Categorie categorie, int prix, int quantite, String descriptions, String nom, String codeFournisseur, boolean actif) {
        this.categorie = categorie;
        this.prix = prix;
        this.quantite = quantite;
        this.descriptions = descriptions;
        this.nom = nom;
        this.codeFournisseur = codeFournisseur;
        this.actif = actif;
    }

    public Produit(int codeProduit, Categorie categorie, int prix, int quantite, String descriptions, String nom, String codeFournisseur, boolean actif) {
        this(categorie, prix, quantite, descriptions, nom, codeFournisseur, actif);
        this.codeProduit = codeProduit;
    }

    //Getters et Setters
    //***********************************
    
    /*public void setCodeProduit(){
        Boolean flag = true;
        int id = 1000;
        while(flag){
            Random rand = new Random();
            id = rand.nextInt(1000);
            if (true){
                System.out.println(pm.get(Produit.class,3));
                flag = false;
            }
           
        }
        this.codeProduit = id;
    }*/

    public int getCodeProduit() {
        return codeProduit;
    }

    public int getPrix() {
        return prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public String getNom() {
        return nom;
    }

    public String getCodeFournisseur() {
        return codeFournisseur;
    }

    public boolean isActif() {
        return actif;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        this.codeFournisseur = codeFournisseur;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    
    public Photo getPrimPhoto(){
        return getPhotos().size() == 0 ? null : getPhotos().get(0);
    }
    
    //public void setPrimPhoto(String photo){
        //this.primPhoto = photo;
    //}


    @Override
    public String toString() {
        return nom;
    }
}
