package fr.diginamic.appliweb.dao;

import fr.diginamic.appliweb.entites.Departement;
import fr.diginamic.appliweb.entites.Ville;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Gestion de la persistance des villes
 */
public interface VilleRepository extends JpaRepository<Ville, Integer> {

    /**
     * Extraction de toutes les villes
     * @return List de {@link Ville}
     */
    List<Ville> findAll();

    /**
     * Extraction d'une ville à partir de son identifiant
     * @param id identifiant
     * @return {@link Ville}
     */
    Ville findById(int id);

    /**
     * Extraction de toutes les villes dont le nom est égal au nom passé en paramètre.<br>
     * Il peut y avoir en effet des villes homonymes situées dans des départements différents.
     * @param nom nom recherché.
     * @return List de {@link Ville}
     */
    List<Ville> findByNom(String nom);

    /**
     * Extraction de la ville dont le nom est égal au nom passé en paramètre et pour le code département donné.
     * @param nom nom recherché.
     * @param departementCode code département
     * @return List de {@link Ville}
     */
    Ville findByNomAndDepartementCode(String nom, String departementCode);

    /**
     * Extraction des villes dont le nom commence par un préfixe donné
     * @param chaine préfixe
     * @return List de {@link Ville}
     */
    List<Ville> findByNomStartingWith(String chaine);

    /**
     * Extraction des villes dont le population est supérieure ou égale au min donné.
     * @param min minimum de population
     * @return List de {@link Ville}
     */
    List<Ville> findByNbHabsGreaterThanOrderByNbHabsDesc(int min);

    /** Recherche de toutes les villes dont la population est supérieure à min et inférieure à max.
     * Les villes sont retournées par population descendante.<br>
     * Les villes sont retournées par population descendante.
     * @param min minimum de population
     * @param max maximum de population
     * @return List de {@link Ville}
     */
    List<Ville> findByNbHabsBetweenOrderByNbHabsDesc(int min, int max);

    /**
     * Recherche de toutes les villes d’un département dont la population est supérieure à min (paramètre de type int)
     * et dont le code du département est passé en paramètre.<br>
     * Les villes sont retournées par population descendante.
     * @param min minimum de population
     * @param departementCode code département
     * @return List de {@link Ville}
     */
    @Query("SELECT v FROM Ville v JOIN v.departement d WHERE v.nbHabs>=:min AND d.code=:departementCode ORDER BY v.nbHabs desc")
    List<Ville> findVillesByMinAndDepartement(int min, String departementCode);

    /** Recherche de toutes les villes d’un département dont la population est comprise entre un min et un max<br>
     * et pour un code département donné.<br>
     * Les villes sont retournées par population descendante.
     * @param min minimum de population
     * @param max maximum de population
     * @param departementCode code département
     * @return List de {@link Ville}
     */
    @Query("SELECT v FROM Ville v JOIN v.departement d WHERE v.nbHabs BETWEEN :min AND :max AND d.code=:departementCode ORDER BY v.nbHabs desc")
    List<Ville> findVillesBetweenMinAndMaxAndDepartement(int min, int max, String departementCode);

    /** Recherche des 10 plus grandes villes d'un département donné.<br>
     * @param departement département
     * @return List de {@link Departement}
     */
    List<Ville> findTop10ByDepartement(Departement departement);
}
