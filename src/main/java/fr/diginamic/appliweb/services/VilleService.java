package fr.diginamic.appliweb.services;

import fr.diginamic.appliweb.exceptions.ExceptionFonctionnelle;
import fr.diginamic.appliweb.mappers.dtos.VilleDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Interface de services de type "application" pour l'exécution des CU de gestion des villes
 */
public interface VilleService {

    /**
     * Extraction paginée d'une liste de villes
     * @param pageRequest page recherchée
     * @return List de {@link VilleDto}
     */
    List<VilleDto> extraire(PageRequest pageRequest);

    /**
     * Extraction de toutes les villes
     * @return Lidt de {@link VilleDto}
     */
    List<VilleDto> extraire();

    /**
     * Extraction d'une ville à partir de son identifiant
     * @param id identifiant
     * @return VilleDto
     * @throws ExceptionFonctionnelle si l'identifiant n'existe pas
     */
    VilleDto extraireParId(int id) throws ExceptionFonctionnelle;

    /**
     * Extraction des villes dont la population est supérieure à un nombre donné
     * @param minK mininum de population en milliers d'habitants
     * @return List de {@link VilleDto}
     */
    List<VilleDto> extractVillesParPopulationMin(int minK);

    /**
     * Extraction des villes dont la population est supérieure à un min donné et un identifiant de département donné.
     * Attention le minimum est exprimé en milliers d'habitants.
     * @param mink minimum de population en milliers d'habitants
     * @param departementCode code du département
     * @return List de {@link VilleDto}
     * @throws ExceptionFonctionnelle si le code département n'existe pas
     */
    List<VilleDto> findVillesByMinAndDepartement(int mink, String departementCode) throws ExceptionFonctionnelle;

    /**
     * Extraction des villes dont la population est comprise entre un min et un max.
     * @param minK minimum de population en milliers d'habitants
     * @param maxK maximum de population en milliers d'habitants
     * @return List de {@link VilleDto}
     * @throws ExceptionFonctionnelle si le code département n'existe pas
     */
    List<VilleDto> extractVillesPopEntreMinEtMax(int minK, int maxK) throws ExceptionFonctionnelle;

    /**
     * Extraction des villes dont le nom commence par un préfixe donné
     * @param debut préfixe des noms de villes à rechercher
     * @return List de {@link VilleDto}
     */
    List<VilleDto> extractVilleNomLike(String debut);

    /**
     * Extraction des villes dont le population est entre un min donné et un max donné et pour un code département donné.<br>
     * Attention les min et max sont exprimés en milliers d'habitants.
     * @param mink minimum en milliers d'habitants
     * @param maxk maximum en milliers d'habitants
     * @param departementCode code du département
     * @return List de {@link VilleDto}
     * @throws ExceptionFonctionnelle si min>max ou code département n'existe pas.
     */
    List<VilleDto> extraireVillesPourDepartementMinMax(int mink, int maxk, String departementCode) throws ExceptionFonctionnelle;

    /**
     * Insertion d'une nouvelle ville
     * @param dto nouvelle ville
     * @return List de {@link VilleDto}
     * @throws ExceptionFonctionnelle si une règle métier n'est pas respectée.
     */
    List<VilleDto> inserer(VilleDto dto) throws ExceptionFonctionnelle;

    /**
     * Modification d'une ville existante
     * @param dto ville à modifier
     * @return List de {@link VilleDto}
     * @throws ExceptionFonctionnelle si une règle métier n'est pas respectée.
     */
    List<VilleDto> modifier(VilleDto dto) throws ExceptionFonctionnelle;

    /**
     * Suppression d'une ville à partir de son identifiant
     * @param id identifiant
     * @return List de {@link VilleDto}
     * @throws ExceptionFonctionnelle si l'identifiant n'existe pas
     */
    List<VilleDto> supprimer(int id) throws ExceptionFonctionnelle;

    /**
     * Export d'un fichier CSV contenant toutes les villes dont la population est supérieure à la valeur min donnée en milliers.
     * @param min minimum en milliers (ex: 30 pour 30000)
     * @param response réponse HTTP
     */
    void exportCsv(int min, HttpServletResponse response);
}
