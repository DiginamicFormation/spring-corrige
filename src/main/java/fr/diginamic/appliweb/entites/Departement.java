package fr.diginamic.appliweb.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;

/**
 * Représente un département
 */
@Entity
public class Departement {

    /** identifiant */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Code */
    private String code;

    /** Nom */
    private String nom;

    /** Villes */
    @OneToMany(mappedBy="departement")
    private List<Ville> villes;

    /**
     * Constructeur obligatoire pour JPA
     */
    public Departement(){

    }

    /**
     *
     * Constructeur
     * @param code code
     * @param nom nom
     */
    public Departement(String code, String nom) {
        this.code = code;
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departement that)) {
            return false;
        }
        return Objects.equals(code, that.code) && Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, nom);
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
     * @return villes
     */
    public List<Ville> getVilles() {
        return villes;
    }

    /**
     * Setter
     *
     * @param villes villes
     */
    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    /**
     * Getter
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter
     *
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
