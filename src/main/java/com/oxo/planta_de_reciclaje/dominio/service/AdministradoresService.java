package com.oxo.planta_de_reciclaje.dominio.service;

import com.oxo.planta_de_reciclaje.dominio.dto.AdministradoresDto;
import com.oxo.planta_de_reciclaje.repository.AdministradoresRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradoresService {

    private final AdministradoresRepository administradoresRepository;

    public AdministradoresService(AdministradoresRepository administradoresRepository) {
        this.administradoresRepository = administradoresRepository;
    }

    public List<AdministradoresDto> getAll() {
        return administradoresRepository.getAll();
    }

    public Optional<AdministradoresDto> getById(int id) {
        return administradoresRepository.getById(id);
    }

    public AdministradoresDto save(AdministradoresDto administradoresDto) {
        return administradoresRepository.save(administradoresDto);
    }

    public boolean delete(int id) {
        if (getById(id).isPresent()) {
            administradoresRepository.delete(id);
            return true;
        }
        return false;
    }
}