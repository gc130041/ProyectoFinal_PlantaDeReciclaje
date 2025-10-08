package com.oxo.planta_de_reciclaje.persistence.mapper;

import com.oxo.planta_de_reciclaje.dominio.dto.MaterialesDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModMaterialesDto;
import com.oxo.planta_de_reciclaje.persistence.entity.Materiales;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaterialesMapper {

    MaterialesDto toDto(Materiales material);
    List<MaterialesDto> toDto(List<Materiales> materiales);

    @InheritInverseConfiguration
    @Mapping(target = "idMaterial", ignore = true)
    Materiales toEntity(MaterialesDto materialesDto);

}