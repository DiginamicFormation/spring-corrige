package fr.diginamic.appliweb.mappers.dtos;

import fr.diginamic.appliweb.dao.DepartementDao;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VilleDto {

    private int id;

    @NotNull
    @Size(min=2)
    private String nom;

    @Min(1)
    private int nbHabs;
    private DepartementDto departement;

    public VilleDto(){

    }

    public VilleDto(String nom, int nbHabs) {
        this.nom = nom;
        this.nbHabs = nbHabs;
    }

    public VilleDto(int id, String nom, int nbHabs) {
        this.id = id;
        this.nom = nom;
        this.nbHabs = nbHabs;
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
     * @return departement
     */
    public DepartementDto getDepartement() {
        return departement;
    }

    /**
     * Setter
     *
     * @param departement departement
     */
    public void setDepartement(DepartementDto departement) {
        this.departement = departement;
    }
}
