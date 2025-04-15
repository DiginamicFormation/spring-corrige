package fr.diginamic.appliweb.services;

import fr.diginamic.appliweb.dao.DepartementRepository;
import fr.diginamic.appliweb.dao.VilleRepository;
import fr.diginamic.appliweb.entites.Departement;
import fr.diginamic.appliweb.entites.Ville;
import fr.diginamic.appliweb.exceptions.ExceptionFonctionnelle;
import fr.diginamic.appliweb.mappers.dtos.DepartementDto;
import fr.diginamic.appliweb.mappers.dtos.VilleDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class VilleServiceTest {

    @Autowired
    private VilleServiceDefaut service;

    @Autowired
    private VilleRepository repository;

    @Autowired
    private DepartementRepository deptRepository;

    @Test
    void extraireParId() {

        try {
            VilleDto dto = service.extraireParId(13326);
            assertNotNull(dto);
        } catch (ExceptionFonctionnelle e) {
            fail();
        }
    }

    @Test
    void extraireParNom() {
    }

    @Test
    void findByNbHabsGreaterThanOrderByNbHabsDesc() {
    }

    @Test
    void findVillesByMinAndDepartement() {
    }

    @Test
    void findByNbHabsBetweenOrderByNbHabsDesc() {
    }

    @Test
    void extractVilleNomLike() {
    }

    @Test
    void extraireVillesPourDepartementMinMax() {
    }

    @Test
    public void testInsert(){
        try {
            VilleDto dto = new VilleDto("Sète", 45000);
            dto.setDepartement(new DepartementDto(1, null, null));

            service.inserer(dto);

            // Vérification de la création du nouveau département
            List<Departement> depts = deptRepository.findAll();
            assertTrue(depts.size()==1);

            // Vérification de la création de la nouvelle ville
            Ville ville = repository.findByNomAndDepartementCode("Sète", "34");
            assertNotNull(ville);
            assertEquals(ville.getNbHabs(), 45000);

            // Supression de la ville
            repository.delete(ville);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInsertVille2(){
        try {
            VilleDto dto = new VilleDto("Sète", 45000);
            dto.setDepartement(new DepartementDto(0, "34", null));

            service.inserer(dto);

            // Vérification de la création du nouveau département
            List<Departement> depts = deptRepository.findAll();
            assertTrue(depts.size()==1);

            // Vérification de la création de la nouvelle ville
            Ville ville = repository.findByNomAndDepartementCode("Sète", "34");
            assertNotNull(ville);
            assertEquals(ville.getNbHabs(), 45000);

            // Supression de la ville
            repository.delete(ville);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testInsertVille3(){
        try {
            VilleDto dto = new VilleDto("Tours", 136000);
            dto.setDepartement(new DepartementDto(0, "37", "Indre-et-Loire"));

            service.inserer(dto);

            // Vérification de la création du nouveau département
            Departement dept = deptRepository.findByCode("37");
            assertNotNull(dept);

            // Vérification de la création de la nouvelle ville
            Ville ville = repository.findByNomAndDepartementCode("Tours", "37");
            assertNotNull(ville);
            assertEquals(ville.getNbHabs(), 136000);

            // Supression de la ville
            repository.delete(ville);

            // Supression du département
            deptRepository.delete(dept);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testInsertVille4(){
        String msg = null;
        try {
            VilleDto dto = new VilleDto("Tours", 136000);
            dto.setDepartement(new DepartementDto(0, null, "Indre-et-Loire"));

            service.inserer(dto);
        } catch (Exception e) {
            msg = e.getMessage();
        }
        assertNotNull(msg);
        assertEquals("Il s'agit d'un nouveau département. Vous devez donc renseigner le code a minima.", msg);
    }

    @Test
    public void testInsertVille5() throws Exception {
        String msg = null;
        try {
            VilleDto dto = new VilleDto("Tours", 136000);
            dto.setDepartement(new DepartementDto(999, null, null));

            service.inserer(dto);
        } catch (Exception e) {
            msg = e.getMessage();
        }
        assertNotNull(msg);
        assertEquals("Le département d'identifiant 999 n'existe pas.", msg);
    }

    @Test @Transactional
    void modifier() {
        try {
            VilleDto dto = new VilleDto(13326, "Montpellier", 10000);
            dto.setDepartement(new DepartementDto(1, null, null));

            service.modifier(dto);

            // Vérification de la création du nouveau département
            List<Departement> depts = deptRepository.findAll();
            assertEquals(depts.size(), 1);

            // Vérification de la création de la nouvelle ville
            Ville ville = repository.findByNomAndDepartementCode("Montpellier", "34");
            assertNotNull(ville);
            assertEquals(10000, ville.getNbHabs());

            // On remet la bonne valeur du nb d'habitants de la ville
            ville.setNbHabs(281613);
        } catch (ExceptionFonctionnelle e) {
            fail();
        }
    }

    @Test
    void supprimer() {

        try {
            service.supprimer(13386);

            Ville ville = repository.findByNomAndDepartementCode("Béziers", "34");
            assertNull(ville);

            VilleDto dto = new VilleDto(0, "Béziers", 76493);
            dto.setDepartement(new DepartementDto(1, null, null));
            service.inserer(dto);

        } catch (ExceptionFonctionnelle e) {
            fail();
        }
    }
}