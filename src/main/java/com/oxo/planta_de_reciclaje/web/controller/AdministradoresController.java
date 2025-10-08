package com.oxo.planta_de_reciclaje.web.controller;

import com.oxo.planta_de_reciclaje.dominio.dto.AdministradoresDto;
import com.oxo.planta_de_reciclaje.dominio.service.AdministradoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
@Tag(name = "Administradores", description = "Operaciones sobre los administradores del sistema.")
public class AdministradoresController {

    private final AdministradoresService administradoresService;

    public AdministradoresController(AdministradoresService administradoresService) {
        this.administradoresService = administradoresService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los administradores", description = "Retorna una lista de todos los administradores.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito.")
    public ResponseEntity<List<AdministradoresDto>> getAll() {
        return ResponseEntity.ok(administradoresService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un administrador por ID")
    @ApiResponse(responseCode = "200", description = "Administrador encontrado.")
    @ApiResponse(responseCode = "404", description = "Administrador no encontrado.")
    public ResponseEntity<AdministradoresDto> getById(@Parameter(description = "ID del administrador a buscar.", example = "1") @PathVariable int id) {
        return administradoresService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo administrador")
    @ApiResponse(responseCode = "201", description = "Administrador creado con éxito.")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.")
    public ResponseEntity<AdministradoresDto> save(@Valid @RequestBody AdministradoresDto administradoresDto) {
        return new ResponseEntity<>(administradoresService.save(administradoresDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un administrador")
    @ApiResponse(responseCode = "204", description = "Administrador eliminado con éxito.")
    @ApiResponse(responseCode = "404", description = "Administrador no encontrado.")
    public ResponseEntity<Void> delete(@Parameter(description = "ID del administrador a eliminar.", example = "1") @PathVariable int id) {
        return administradoresService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}