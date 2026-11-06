package com.DMM.MDM.service;

import com.DMM.MDM.model.Tarea;
import com.DMM.MDM.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService {

    private final TareaRepository repository;

    public TareaService(TareaRepository repository) {
        this.repository = repository;
    }

    public List<Tarea> obtenerTodas(Boolean completada) {
        List<Tarea> todas = repository.findAll();
        if (completada == null) {
            return todas;
        }
        return todas.stream()
                .filter(t -> t.isCompletada() == completada)
                .collect(Collectors.toList());
    }

    public Tarea obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));
    }

    public Tarea crear(Tarea tarea) {
        validarTitulo(tarea.getTitulo());
        tarea.setId(null);
        tarea.setCompletada(false);
        return repository.save(tarea);
    }

    public Tarea actualizar(Long id, Tarea detalles) {
        Tarea existente = obtenerPorId(id);
        validarTitulo(detalles.getTitulo());

        existente.setTitulo(detalles.getTitulo());
        existente.setDescripcion(detalles.getDescripcion());

        return repository.save(existente);
    }

    public Tarea marcarComoCompletada(Long id) {
        Tarea existente = obtenerPorId(id);
        if (existente.isCompletada()) {
            throw new IllegalStateException("La tarea ya se encuentra completada");
        }
        existente.setCompletada(true);
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        repository.deleteById(id);
    }

    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio y no puede estar vacío");
        }
    }
}