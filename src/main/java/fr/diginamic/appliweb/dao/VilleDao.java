package fr.diginamic.appliweb.dao;

import fr.diginamic.appliweb.entites.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VilleDao {

    @PersistenceContext
    private EntityManager em;

    public List<Ville> extraire(){

        // Création de la requête :
        TypedQuery<Ville> query = em.createQuery("SELECT vil FROM Ville vil", Ville.class);

        // Exécution de la requête :
        return query.getResultList();
    }

    public Ville extraireParId(int id){

        // Création de la requête :
        TypedQuery<Ville> query = em.createQuery("SELECT vil FROM Ville vil WHERE vil.id=:id", Ville.class);
        query.setParameter("id", id);

        // Exécution de la requête :
        return query.getResultStream().findFirst().orElse(null);
    }

    public Ville extraireParNom(String nom){

        // Création de la requête :
        TypedQuery<Ville> query = em.createQuery("SELECT vil FROM Ville vil WHERE vil.nom=:nom", Ville.class);
        query.setParameter("nom", nom);

        // Exécution de la requête :
        return query.getResultStream().findFirst().orElse(null);
    }

    public void inserer(Ville ville) {
        em.persist(ville);
    }

    public boolean modifier(Ville ville) {
        Ville villeDB = extraireParId(ville.getId());
        if (villeDB!=null) {
            villeDB.setNom(ville.getNom());
            villeDB.setNbHabs(ville.getNbHabs());
            villeDB.setDepartement(ville.getDepartement());
            return true;
        }
        return false;
    }

    public boolean supprimer(int id) {
        Ville villeDB = extraireParId(id);
        if (villeDB!=null) {
            em.remove(villeDB);
            return true;
        }
        return false;
    }
}
