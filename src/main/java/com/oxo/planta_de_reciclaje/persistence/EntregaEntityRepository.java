package com.oxo.planta_de_reciclaje.persistence;

import com.oxo.planta_de_reciclaje.dominio.dto.EntregaDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModEntregaDto;
import com.oxo.planta_de_reciclaje.dominio.exception.EntregaAlreadyExistsException;
import com.oxo.planta_de_reciclaje.dominio.exception.EntregaNotExistsException;
import com.oxo.planta_de_reciclaje.persistence.crud.CrudEntregaEntity;
import com.oxo.planta_de_reciclaje.persistence.entity.Entrega;
import com.oxo.planta_de_reciclaje.persistence.mapper.EntregaMapper;
import com.oxo.planta_de_reciclaje.repository.EntregaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntregaEntityRepository implements EntregaRepository {
    private final CrudEntregaEntity crud;
    private final EntregaMapper mapper;

    public EntregaEntityRepository(CrudEntregaEntity crud, EntregaMapper mapper) {
        this.crud = crud;
        this.mapper = mapper;
    }

    @Override
    public List<EntregaDto> obtenerTodos() {
        return mapper.toDto((List<Entrega>) crud.findAll());
    }

    @Override
    public EntregaDto buscarPorId(Integer idEntrega) {
        return crud.findById(idEntrega).map(mapper::toDto).orElseThrow(() -> new EntregaNotExistsException(idEntrega));
    }

    @Override
    public EntregaDto guardarEntrega(EntregaDto dto) {
        if (crud.findByIdEntrega(dto.idEntrega()).isPresent()){
            throw new EntregaAlreadyExistsException(dto.idEntrega());
        }
        Entrega entrega = mapper.toEntity(dto);
        return mapper.toDto(crud.save(entrega));
    }

    @Override
    public EntregaDto modificarEntrega(Integer idEntrega, ModEntregaDto mod) {
        Entrega entrega = crud.findById(idEntrega).orElseThrow(() -> new EntregaNotExistsException(idEntrega));

        entrega.setNombreProveedor(mod.nombreProveedor());

        return mapper.toDto(crud.save(entrega));
    }

    @Override
    public void eliminarEntrega(Integer idEntrega) {
        if (!crud.existsById(idEntrega)){
            throw new EntregaNotExistsException(idEntrega);
        }
        crud.deleteById(idEntrega);
    }
}
