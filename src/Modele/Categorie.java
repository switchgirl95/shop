package Modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "CATEGORIE")
public class Categorie {
    @Id
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
