package Modele;

import javax.persistence.*;

@Entity(name = "CATEGORIE")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCATEGORIE")
    private int idcategorie;
    @Column(name = "NOMCATEGORIE")
    private String nomCategorie;


    //Constructeurs
    //**************************************
    public Categorie() {
        this.idcategorie =0;
    }

    public Categorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public Categorie(int idcategorie, String nomCategorie) {
        this(nomCategorie);
        this.idcategorie = idcategorie;
    }


    //Getters et Setters
    //**************************************
    public int getIdcategorie() {
        return idcategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    @Override
    public String toString() {
        return nomCategorie;
    }
}
