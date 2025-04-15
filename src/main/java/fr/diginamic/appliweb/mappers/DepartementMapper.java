package fr.diginamic.appliweb.mappers;

import fr.diginamic.appliweb.entites.Departement;
import fr.diginamic.appliweb.entites.Ville;
import fr.diginamic.appliweb.mappers.dtos.DepartementDto;
import fr.diginamic.appliweb.mappers.dtos.VilleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartementMapper {

    @Autowired
    private VilleSimpleMapper villeMapper;

    public DepartementDto toDto(Departement dept){
        DepartementDto dto = new DepartementDto(dept.getId(), dept.getCode(), dept.getNom());
        dto.setVilles(villeMapper.toDtos(dept.getVilles()));
        return dto;
    }

    public List<DepartementDto> toDtos(List<Departement> departements){
        return departements.stream().map(d->toDto(d)).toList();
    }

    public Departement toBean(DepartementDto dto) {
        Departement bean = new Departement();
        bean.setId(dto.getId());
        bean.setCode(dto.getCode());
        bean.setNom(dto.getNom());
        return bean;
    }
}
