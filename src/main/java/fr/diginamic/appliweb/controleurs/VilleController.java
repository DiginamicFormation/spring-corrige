package fr.diginamic.appliweb.controleurs;

import fr.diginamic.appliweb.exceptions.ExceptionFonctionnelle;
import fr.diginamic.appliweb.mappers.dtos.VilleDto;
import fr.diginamic.appliweb.services.VilleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Point d'entrée de l'API sur les villes
 */
@RestController
@RequestMapping("/villes")
public class VilleController {

    /** Application service pour le traitement des villes */
    @Autowired
    private VilleService villeService;

    /**
     * Extraction des villes
     * @return List de {@link VilleDto}
     */
    @GetMapping("/toutes")
    public List<VilleDto> extractVilles(){

        return villeService.extraire();
    }

    /**
     * Extraction paginée des villes
     * @param numPage numéro de page (commence à 0)
     * @param nbLignes nombre de lignes
     * @return List de {@link VilleDto}
     */
    @GetMapping("/pagination")
    public List<VilleDto> extractVilles(@RequestParam(required = true) int numPage, @RequestParam(required = true) int nbLignes){

        PageRequest pageRequest = PageRequest.of(numPage, nbLignes);
        return villeService.extraire(pageRequest);
    }

    /**
     * Extraction d'une ville en fonction de son id
     * @param id identifiant
     * @return VilleDto
     * @throws ExceptionFonctionnelle si l'identifiant est inconnu
     */
    @GetMapping("/id/{id}")
    public VilleDto extractVilleParId(@PathVariable int id) throws ExceptionFonctionnelle {
        return villeService.extraireParId(id);
    }

    /**
     * Extraction d'une ville dont le nom commence par une chaine donnée
     * @param prefixe début du nom
     * @return {@link VilleDto}
     */
    @GetMapping("/like/{debut}")
    public List<VilleDto> extractVilleNomLike(@PathVariable String prefixe){
        return villeService.extractVilleNomLike(prefixe);
    }

    /**
     * Extraction des villes dont la population est supérieure à un nombre donné
     * @param minK mininum de population en milliers d'habitants
     * @return List de {@link VilleDto}
     */
    @GetMapping("/greater/{minK}")
    public List<VilleDto> extractVillesParPopulationMin(@PathVariable int minK){
        return villeService.extractVillesParPopulationMin(minK);
    }

    /**
     * Export CSV de toutes les villes dont la population est supérieure à un nombre donné.
     * @param minK minimum de population en milliers d'habitants
     * @param response réponse HTTP
     */
    @GetMapping("/export/greater/{minK}")
    public void exportVillesParPopMin(@PathVariable int minK, HttpServletResponse response){

        villeService.exportCsv(minK, response);
    }

    /**
     * Extraction des villes dont la population est entre un min et un max
     * @param minK minimum de population en milliers d'habitants
     * @param maxK maximum de population
     * @return List de {@link VilleDto}
     * @throws ExceptionFonctionnelle si min est supérieur à max
     */
    @GetMapping("/between/{minK}/and/{maxK}")
    public List<VilleDto> extractVillesPopEntreMinEtMax(@PathVariable int minK, @PathVariable int maxK) throws ExceptionFonctionnelle {
        return villeService.extractVillesPopEntreMinEtMax(minK, maxK);
    }

    /**
     * Extraction des villes dont la population est supérieure à un min donné et un identifiant de département donné
     * @param min minimum de population en milliers d'habitants
     * @param departementCode code du département
     * @return List de {@link VilleDto}
     * @throws ExceptionFonctionnelle si le code département n'existe pas
     */
    @GetMapping("/greater/{min}/departement/{departementCode}")
    public List<VilleDto> findVillesByMinAndDepartement(@PathVariable int min, @PathVariable String departementCode) throws ExceptionFonctionnelle {
        return villeService.findVillesByMinAndDepartement(min, departementCode);
    }

    /**
     * Extraction des villes dont le population est entre un min donné et un max donné et pour un code département donné.
     * @param min minimum
     * @param max maximum
     * @param departementCode code du département
     * @return List de {@link VilleDto}
     * @throws ExceptionFonctionnelle si min>max ou code département n'existe pas.
     */
    @GetMapping("/between/{min}/and/{max}/departement/{departementCode}")
    public List<VilleDto> extractParNomEtMinMax(@PathVariable int min, @PathVariable int max, @PathVariable String departementCode) throws ExceptionFonctionnelle {

        return villeService.extraireVillesPourDepartementMinMax(min, max, departementCode);
    }

    /**
     * Insertion d'une nouvelle ville.<br>
     * Le département associé doit avoir a minima un identifiant ou un code.
     * @param nvVille nouvelle ville
     * @return List des {@link VilleDto}
     * @throws ExceptionFonctionnelle si le nom de la ville existe déjà ou si le département associé n'a ni identifiant ni code.
     */
    @PostMapping
    public ResponseEntity<?> insertVille(@RequestBody VilleDto nvVille) throws ExceptionFonctionnelle {

        return ResponseEntity.ok(villeService.inserer(nvVille));
    }

    /**
     * Modification d'une ville.<br>
     * Le département associé doit avoir a minima un identifiant ou un code.
     * @param ville ville à modifier avec les données modifiées.
     * @return List des {@link VilleDto}
     * @throws ExceptionFonctionnelle si le nom de la ville existe déjà ou si le département associé n'a ni identifiant ni code.
     */
    @PutMapping
    public ResponseEntity<?> modifVille(@RequestBody VilleDto ville) throws ExceptionFonctionnelle {

        return ResponseEntity.ok(villeService.modifier(ville));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprVille(@PathVariable int id) throws ExceptionFonctionnelle {
        return ResponseEntity.ok(villeService.supprimer(id));
    }
}
