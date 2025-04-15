package fr.diginamic.appliweb.mappers;

import fr.diginamic.appliweb.entites.Departement;
import fr.diginamic.appliweb.mappers.dtos.DepartementDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartementSimpleMapper {

    public DepartementDto toDto(Departement dept){
        DepartementDto dto = new DepartementDto(dept.getId(), dept.getCode(), dept.getNom());
        return dto;
    }

    public List<DepartementDto> toDtos(List<Departement> departements){
        return departements.stream().map(d->toDto(d)).toList();
    }

    public Departement toBean(DepartementDto dto) {
        Departement bean = new Departement();
        bean.setId(dto.getId());
        bean.setNom(dto.getNom());
        bean.setCode(dto.getCode());
        return bean;
    }
}
