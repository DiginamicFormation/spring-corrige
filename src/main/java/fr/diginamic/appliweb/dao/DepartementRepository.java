package fr.diginamic.appliweb.dao;

import fr.diginamic.appliweb.entites.Departement;
import fr.diginamic.appliweb.entites.Ville;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Gestion de la persistance des départements
 */
public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    /**
     * Extrait tous les départements
     *
     * @return list de {@link Departement}
     */
    List<Departement> findAll();

    /**
     * Extrait un département à partir de son identifiant
     *
     * @param id identifiant
     * @return Departement
     */
    Departement findById(int id);

    /**
     * Extrait un département à partir de son nom
     *
     * @param nom nom
     * @return Departement
     */
    Departement findByNom(String nom);

    /**
     * Extrait un département à partir de son code
     *
     * @param code code
     * @return Departement
     */
    Departement findByCode(String code);
}