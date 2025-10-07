package com.oxo.planta_de_reciclaje.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.oxo.planta_de_reciclaje.dominio.dto.MaterialesDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModMaterialesDto;
import com.oxo.planta_de_reciclaje.dominio.service.MaterialesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiales")
@Tag(name = "Materiales", description = "Operaciones CRUD sobre los materiales")
public class MaterialesController {

    private final MaterialesService materialesService;

    public MaterialesController(MaterialesService materialesService) {
        this.materialesService = materialesService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los materiales", description = "Retorna una lista de todos los materiales registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de materiales obtenida con éxito.")
    public ResponseEntity<List<MaterialesDto>> obtenerTodos() {
        return ResponseEntity.ok(materialesService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un material por ID", description = "Busca y retorna un material específico por su identificador único.")
    @ApiResponse(responseCode = "200", description = "Material encontrado y devuelto.")
    @ApiResponse(responseCode = "404", description = "Material no encontrado.", content = @Content)
    public ResponseEntity<MaterialesDto> obtenerPorId(@Parameter(description = "ID del material a buscar.", example = "1") @PathVariable Integer id) {
        MaterialesDto material = materialesService.buscarPorId(id);
        return (material != null) ? ResponseEntity.ok(material) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo material", description = "Crea y guarda un nuevo material en la base de datos.")
    @ApiResponse(responseCode = "201", description = "Material creado con éxito.")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<MaterialesDto> crear(@Valid @RequestBody MaterialesDto materialesDto) {
        MaterialesDto creado = materialesService.guardarMaterial(materialesDto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar un material existente", description = "Actualiza la información de un material existente por su ID.")
    @ApiResponse(responseCode = "200", description = "Material actualizado con éxito.")
    @ApiResponse(responseCode = "404", description = "Material no encontrado.", content = @Content)
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<MaterialesDto> modificar(@Parameter(description = "ID del material a modificar.", example = "1") @PathVariable Integer id, @Valid @RequestBody ModMaterialesDto modMaterialesDto) {
        MaterialesDto actualizado = materialesService.modificarMaterial(id, modMaterialesDto);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un material", description = "Elimina un material de la base de datos por su ID.")
    @ApiResponse(responseCode = "204", description = "Material eliminado con éxito.")
    @ApiResponse(responseCode = "404", description = "Material no encontrado.", content = @Content)
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del material a eliminar.", example = "1") @PathVariable Integer id) {
        materialesService.eliminarMaterial(id);
        return ResponseEntity.noContent().build();
    }
}