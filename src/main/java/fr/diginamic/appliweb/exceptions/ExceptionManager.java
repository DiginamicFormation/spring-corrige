package fr.diginamic.appliweb.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Un controller advice est chargé de catcher les exceptions renvoyées par les contrôleurs.
 */
@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(ExceptionFonctionnelle.class)
    public ResponseEntity<String> traiterErreur(ExceptionFonctionnelle e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ExceptionTechnique.class)
    public ResponseEntity<String> traiterErreur(ExceptionTechnique e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
