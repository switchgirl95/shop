package Modele;

import javax.persistence.*;
import java.util.List;

/**
 * Created by arnold on 06/03/18.
 */
@Entity(name = "FACTURE")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDFACTURE") private int idFacture;
    @ManyToOne
    @JoinColumn(name="IDGEST", nullable=false)
    private Gestionnaire gestionnaire;
    /*@Column(name = "IDGEST") private int idGest;*/
    @Column(name = "DATEFACTURE") private String dateFacture;
    @Column(name = "REMISE") private double remise;
    @Column(name = "MONTANT") private double montant;
    @Column(name = "TYPEFACT") private boolean typeFact;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "IDFACTURE")
    private List<ListeFacture> listeFactures;

    public Facture() {
        idFacture = 0;
    }

    public Facture(Gestionnaire gest, String dateFacture, double remise, double montant, boolean typeFact) {
        this.gestionnaire = gest;
        this.dateFacture = dateFacture;
        this.remise = remise;
        this.montant = montant;
        this.typeFact = typeFact;
    }

    public Facture(int idFacture, Gestionnaire gest, String dateFacture, double remise, double montant, boolean typeFact) {
        this(gest, dateFacture, remise, montant, typeFact);
        this.idFacture = idFacture;
    }

    public int getIdFacture() {
        return idFacture;
    }

    /*public int getIdGest() {
        return idGest;
    }

    public void setIdGest(int idGest) {
        this.idGest = idGest;
    }*/

    public Gestionnaire getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public boolean isTypeFact() {
        return typeFact;
    }

    public void setTypeFact(boolean typeFact) {
        this.typeFact = typeFact;
    }


    public List<ListeFacture> getListeFactures() {
        return listeFactures;
    }

    public String getReal(){
        return this.typeFact ? "ajout" : "retrait";
    }
}
