package fr.diginamic.appliweb.mappers;

import fr.diginamic.appliweb.entites.Ville;
import fr.diginamic.appliweb.mappers.dtos.VilleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VilleMapper {

    @Autowired
    private DepartementSimpleMapper deptMapper;

    public VilleDto toDto(Ville ville){
        VilleDto dto = new VilleDto(ville.getId(), ville.getNom(), ville.getNbHabs());
        dto.setDepartement(deptMapper.toDto(ville.getDepartement()));
        return dto;
    }

    public List<VilleDto> toDtos(List<Ville> villes){

        return villes.stream().map(v->toDto(v)).toList();
    }

    public Ville toBean(VilleDto dto) {
        Ville ville = new Ville();
        ville.setId(dto.getId());
        ville.setNom(dto.getNom());
        ville.setNbHabs(dto.getNbHabs());
        ville.setDepartement(deptMapper.toBean(dto.getDepartement()));
        return ville;
    }
}
