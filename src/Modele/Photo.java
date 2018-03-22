package Modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "PHOTO")
public class Photo
{
    @Id
    @Column(name = "IDPHOTO") private int idPhoto;
    @Column(name = "CODEPRODUIT") private int codeProduit;
    @Column(name = "LIEN") private String lien;

    //Constructeurs
    //*********************************
    public Photo() {
        this.idPhoto =0;
        this.codeProduit =0;
    }

    public Photo(int idPhoto, int codeProduit, String lien) {
        this(codeProduit, lien);
        this.idPhoto = idPhoto;
    }

    public Photo(int codeProduit, String lien) {
        this.codeProduit = codeProduit;
        this.lien = lien;
    }

    //Getters et Setters
    //*********************************
    public int getIdPhoto() {
        return idPhoto;
    }

    public int getCodeProduit() {
        return codeProduit;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }
}
