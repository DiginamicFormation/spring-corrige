package fr.diginamic.appliweb.mappers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartementDto {

    private int id;
    private String nom;
    private String code;
    private List<VilleDto> villes = new ArrayList<>();

    public DepartementDto(int id, String code, String nom) {
        this.id = id;
        this.code = code;
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
    public List<VilleDto> getVilles() {
        return villes;
    }

    /**
     * Setter
     *
     * @param villes villes
     */
    public void setVilles(List<VilleDto> villes) {
        this.villes = villes;
    }
}
