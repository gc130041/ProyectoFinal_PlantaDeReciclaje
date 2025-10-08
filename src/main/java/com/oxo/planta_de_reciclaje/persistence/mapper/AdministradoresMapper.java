package com.oxo.planta_de_reciclaje.persistence.mapper;

import com.oxo.planta_de_reciclaje.dominio.dto.AdministradoresDto;
import com.oxo.planta_de_reciclaje.persistence.entity.Administradores;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdministradoresMapper {

    AdministradoresDto toDto(Administradores administrador);
    List<AdministradoresDto> toDto(List<Administradores> administradores);

    @InheritInverseConfiguration
    Administradores toEntity(AdministradoresDto administradoresDto);
}