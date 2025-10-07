package com.oxo.planta_de_reciclaje.dominio.service;

import com.oxo.planta_de_reciclaje.dominio.dto.MaterialesDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModMaterialesDto;
import com.oxo.planta_de_reciclaje.repository.MaterialesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialesService {

    private final MaterialesRepository materialesRepository;

    @Autowired
    public MaterialesService(MaterialesRepository materialesRepository) {
        this.materialesRepository = materialesRepository;
    }

    public List<MaterialesDto> obtenerTodos() {
        return materialesRepository.obtenerTodos();
    }

    public MaterialesDto buscarPorId(Integer idMaterial) {
        return materialesRepository.buscarPorId(idMaterial);
    }

    public MaterialesDto guardarMaterial(MaterialesDto materialesDto) {
        return materialesRepository.guardar(materialesDto);
    }

    public MaterialesDto modificarMaterial(Integer idMaterial, ModMaterialesDto modMaterialesDto) {
        return materialesRepository.modificar(idMaterial, modMaterialesDto);
    }

    public void eliminarMaterial(Integer idMaterial) {
        materialesRepository.eliminar(idMaterial);
    }
}