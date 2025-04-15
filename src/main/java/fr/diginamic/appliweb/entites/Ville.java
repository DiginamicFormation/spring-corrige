package fr.diginamic.appliweb.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

/**
 * Représente une ville
 */
@Entity
public class Ville {

    /** identifiant */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** nom */
    private String nom;

    /** Nombre d'habitants */
    private int nbHabs;

    /** département d'appartenance */
    @ManyToOne
    @JoinColumn(name="id_dept")
    private Departement departement;

    /** utilisé par JPA pour instancier des villes */
    public Ville(){

    }

    /**
     * Constructeur
     * @param nom nom
     * @param nbHabs population
     * @param dept département
     */
    public Ville(String nom, int nbHabs, Departement dept) {
        this.nom = nom;
        this.nbHabs = nbHabs;
        this.departement = dept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ville ville)) {
            return false;
        }
        return Objects.equals(nom, ville.nom) && Objects.equals(departement, ville.departement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, departement);
    }

    /**
     * Getter
     *
     * @return nbHabs
     */
    public int getNbHabs() {
        return nbHabs;
    }

    /**
     * Setter
     *
     * @param nbHabs nbHabs
     */
    public void setNbHabs(int nbHabs) {
        this.nbHabs = nbHabs;
    }

    /**
     * Getter
     *
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter
     *
     * @param nom nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return departement
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * Setter
     *
     * @param departement departement
     */
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
