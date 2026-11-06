package com.DMM.MDM.controller;

import com.DMM.MDM.model.Tarea;
import com.DMM.MDM.service.TareaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService service;

    public TareaController(TareaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> listar(@RequestParam(required = false) Boolean completada) {
        return ResponseEntity.ok(service.obtenerTodas(completada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable Long id, @RequestBody Tarea tarea) {
        return ResponseEntity.ok(service.actualizar(id, tarea));
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<Tarea> completar(@PathVariable Long id) {
        return ResponseEntity.ok(service.marcarComoCompletada(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}