package fr.diginamic.appliweb.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

import static org.hamcrest.Matchers.containsString;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class VilleControleurTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private VilleRepository repository;

    @Autowired
    private DepartementRepository deptRepository;

    @Test
    public void testExtraire(){
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("/villes/toutes"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Montpellier")))
                    .andExpect(content().string(containsString("Béziers")))
                    .andExpect(jsonPath("$[0].nom").value("Montpellier"))
                    .andExpect(jsonPath("$[0].nbHabs").value(281613));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testExtrairePagination(){
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("/villes/pagination?numPage=1&nbLignes=1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].nom").value("Béziers"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testExtraireParId(){
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("/villes/id/13326"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nom").value("Montpellier"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testExtraireLikeNom(){
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("/villes/like/Béz"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].nom").value("Béziers"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testExtraireGreaterMin(){
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("/villes/greater/10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].nom").value("Montpellier"))
                    .andExpect(jsonPath("$[1].nom").value("Béziers"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInsertVille(){
        try {
            VilleDto dto = new VilleDto("Sète", 45000);
            dto.setDepartement(new DepartementDto(1, null, null));

            this.mvc.perform(MockMvcRequestBuilders.post("/villes")
                                                    .content(mapper.writeValueAsString(dto))
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));

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
    public void testInsertVille5() throws Exception {
        String msg = null;
        VilleDto dto = new VilleDto("Tours", 136000);
        dto.setDepartement(new DepartementDto(999, null, null));

        this.mvc.perform(MockMvcRequestBuilders.post("/villes")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Le département d'identifiant 999 n'existe pas."));
    }

    @Transactional
    @Test
    public void testModifVille(){
        try {
            VilleDto dto = new VilleDto(13326, "Montpellier", 10000);
            dto.setDepartement(new DepartementDto(1, null, null));

            this.mvc.perform(MockMvcRequestBuilders.put("/villes")
                            .content(mapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2));

            // Vérification de la création du nouveau département
            List<Departement> depts = deptRepository.findAll();
            assertEquals(depts.size(), 1);

            // Vérification de la création de la nouvelle ville
            Ville ville = repository.findByNomAndDepartementCode("Montpellier", "34");
            assertNotNull(ville);
            assertEquals(10000, ville.getNbHabs());

            // On remet la bonne valeur du nb d'habitants de la ville
            ville.setNbHabs(281613);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Transactional
    @Test
    public void testSupprVille(){
        try {
            this.mvc.perform(MockMvcRequestBuilders.delete("/villes/13326"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1));

            // Vérification de la création du nouveau département
            List<Departement> depts = deptRepository.findAll();
            assertEquals(depts.size(), 1);

            // Vérification de la création de la nouvelle ville
            Ville ville = repository.findByNomAndDepartementCode("Montpellier", "34");
            assertNull(ville);

            VilleDto dto = new VilleDto(13326, "Montpellier", 281613);
            dto.setDepartement(new DepartementDto(1, null, null));

            this.mvc.perform(MockMvcRequestBuilders.delete("/villes/13326")
                            .content(mapper.writeValueAsString(dto))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
