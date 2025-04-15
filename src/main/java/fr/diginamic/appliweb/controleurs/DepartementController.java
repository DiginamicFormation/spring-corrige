package fr.diginamic.appliweb.controleurs;

import fr.diginamic.appliweb.exceptions.ExceptionFonctionnelle;
import fr.diginamic.appliweb.mappers.dtos.DepartementDto;
import fr.diginamic.appliweb.services.DepartementService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Point d'entrée de l'API sur les départements
 */
@RestController
@RequestMapping("/departements")
public class DepartementController {

    /** Application service pour le traitement des CU sur les départements */
    @Autowired
    private DepartementService service;

    /**
     * Extraction des départements
     * @return List de {@link DepartementDto}
     */
    @GetMapping("/tous")
    public List<DepartementDto> extraire(){

        return service.extraire();
    }

    /**
     * Extraction d'un département à partir de son identifiant
     * @param id identifiant
     * @return {@link DepartementDto}
     * @throws ExceptionFonctionnelle si l'identifiant n'existe pas
     */
    @GetMapping("/id/{id}")
    public DepartementDto extractParId(@PathVariable int id) throws ExceptionFonctionnelle {

        return service.extraireParId(id);
    }

    /**
     * Extraction d'un département à partir de son code
     * @param code code du département
     * @return {@link DepartementDto}
     * @throws ExceptionFonctionnelle si le code du département n'existe pas
     */
    @GetMapping("/code/{code}")
    public DepartementDto extractParCode(@PathVariable String code) throws ExceptionFonctionnelle {

        return service.extraireParCode(code);
    }

    /**
     * Export PDF de la fiche du département avec toutes les villes connues
     * @param code code du département
     * @param response réponse HTTP
     */
    @GetMapping("/export/{code}")
    public void exportVillesParPopMin(@PathVariable String code, HttpServletResponse response){

        service.exportVillesParDepartement(code, response);
    }

    /**
     * Insertion d'un nouveau département
     * @param nvDepartement données du nouveau département (code obligatoire)
     * @return List de {@link DepartementDto}
     * @throws ExceptionFonctionnelle si le code du département n'est pas renseigné.
     */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody DepartementDto nvDepartement) throws ExceptionFonctionnelle {

        return ResponseEntity.ok(service.inserer(nvDepartement));
    }

    /**
     * Modification d'un département
     * @param departement données modifiées du département (identifiant obligatoire)
     * @return List de {@link DepartementDto}
     * @throws ExceptionFonctionnelle si le code du département n'est pas renseigné.
     */
    @PutMapping
    public ResponseEntity<?> modifVille(@RequestBody DepartementDto departement) throws ExceptionFonctionnelle {

        return ResponseEntity.ok(service.modifier(departement));
    }

    /**
     * Suppression d'un département à partir de son identifiant
     * @param id identifiant
     * @return List de {@link DepartementDto}
     * @throws ExceptionFonctionnelle si l'identifiant n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprVille(@PathVariable int id) throws ExceptionFonctionnelle {

        return ResponseEntity.ok(service.supprimer(id));
    }
}
