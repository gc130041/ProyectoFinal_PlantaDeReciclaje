package com.oxo.planta_de_reciclaje.web.controller;

import com.oxo.planta_de_reciclaje.dominio.dto.EntregaDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModEntregaDto;
import com.oxo.planta_de_reciclaje.dominio.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entregas")
@Tag(name = "Entregas", description = "Operaciones CRUD sobre las entregas")
public class EntregaController {

    private final EntregaService service;

    public EntregaController(EntregaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las entregas", description = "Retorna una lista de todas las entregas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de entregas obtenida con éxito.")
    public ResponseEntity<List<EntregaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una entrega por ID", description = "Busca y retorna una entrega específica por su identificador único.")
    @ApiResponse(responseCode = "200", description = "Entrega encontrada y devuelta.")
    @ApiResponse(responseCode = "404", description = "Entrega no encontrada.", content = @Content)
    public ResponseEntity<EntregaDto> obtenerPorId(@Parameter(description = "ID de la entrega a buscar.", example = "1") @PathVariable Integer id) {
        EntregaDto entrega = service.buscarPorId(id);
        return (entrega != null) ? ResponseEntity.ok(entrega) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva entrega", description = "Crea y guarda una nueva entrega en la base de datos.")
    @ApiResponse(responseCode = "201", description = "Entrega creada con éxito.")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<EntregaDto> crear(@Valid @RequestBody EntregaDto dto) {
        EntregaDto creado = service.guardarEntrega(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar una entrega existente", description = "Actualiza la información de una entrega existente por su ID.")
    @ApiResponse(responseCode = "200", description = "Entrega actualizada con éxito.")
    @ApiResponse(responseCode = "404", description = "Entrega no encontrada.", content = @Content)
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content(schema = @Schema(implementation = String.class)))
    public ResponseEntity<EntregaDto> modificar(@Parameter(description = "ID de la entrega a modificar.", example = "1") @PathVariable Integer id, @Valid @RequestBody ModEntregaDto mod) {
        EntregaDto actualizado = service.modificarEntrega(id, mod);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una entrega", description = "Elimina una entrega existente por su ID.")
    @ApiResponse(responseCode = "204", description = "Entrega eliminada con éxito.")
    @ApiResponse(responseCode = "404", description = "Entrega no encontrada.", content = @Content)
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la entrega a eliminar.", example = "1") @PathVariable Integer id) {
        service.eliminarEntrega(id);
        return ResponseEntity.noContent().build();
    }
}
