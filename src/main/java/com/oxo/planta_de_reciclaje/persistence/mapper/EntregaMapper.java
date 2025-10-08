package com.oxo.planta_de_reciclaje.persistence.mapper;

import com.oxo.planta_de_reciclaje.dominio.dto.EntregaDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModEntregaDto;
import com.oxo.planta_de_reciclaje.persistence.entity.Entrega;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntregaMapper {

    @Mapping(source = "material.idMaterial", target = "idMaterial")
    EntregaDto toDto(Entrega entrega);
    List<EntregaDto> toDto(List<Entrega> entrega);

    @InheritInverseConfiguration
    @Mapping(target = "material", ignore = true)
    Entrega toEntity(EntregaDto dto);

}
