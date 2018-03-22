package Modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by arnold on 06/03/18.
 */

@Entity(name = "GESTIONNAIRE")
public class Gestionnaire {
    @Id
    @Column(name = "IDGEST") private int idGest;
    @Column(name = "NOM") private String nom;
    @Column(name = "TYPEGEST") private boolean typeGest;
    @Column(name = "USERNAME") private String username;
    @Column(name = "PASSWORD") private String password;
    @Column(name = "ACTIF") private boolean actif;
    @Column(name = "TELEPHONE") private String telephone;
    @Column(name = "EMAIL") private String email;

    public Gestionnaire() {
        idGest = 0;
    }

    public Gestionnaire(String nom, boolean typeGest, String username, String password, boolean actif, String telephone, String email) {
        this.nom = nom;
        this.typeGest = typeGest;
        this.username = username;
        this.password = password;
        this.actif = actif;
        this.telephone = telephone;
        this.email = email;
    }

    public Gestionnaire(int idGest, String nom, boolean typeGest, String username, String password, boolean actif, String telephone, String email) {
        this(nom, typeGest, username, password, actif, telephone, email);
        this.idGest = idGest;
    }

    Gestionnaire(Long idGest, String nom, Boolean typeGest, String username, String password, Boolean actif, String telephone, String email) {
        this(idGest.intValue(), nom, typeGest, username, password, actif, telephone, email);
    }

    public int getIdGest() {
        return idGest;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isTypeGest() {
        return typeGest;
    }

    public void setTypeGest(boolean typeGest) {
        this.typeGest = typeGest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
