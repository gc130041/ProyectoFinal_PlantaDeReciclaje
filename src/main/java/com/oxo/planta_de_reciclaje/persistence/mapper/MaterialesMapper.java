package com.oxo.planta_de_reciclaje.persistence.mapper;

import com.oxo.planta_de_reciclaje.dominio.dto.MaterialDto;
import com.oxo.planta_de_reciclaje.persistence.entity.MaterialesEntity;
import org.mapstruct.*;

import java.util.List;

//@Mapper(componentModel = "spring")
public interface MaterialesMapper {

    MaterialDto toDto(MaterialesEntity materiales);
    List<MaterialDto> toDto(List<MaterialesEntity> alumnos);

    @InheritInverseConfiguration
    //@Mapping(source = "tipoMaterial", ignore = "true")
    //@Mapping(source = "precioPorKg", ignore = "true")
    MaterialesEntity toEntity(MaterialDto materialDto);

}
