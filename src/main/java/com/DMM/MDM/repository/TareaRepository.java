package com.DMM.MDM.repository;

import com.DMM.MDM.model.Tarea;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TareaRepository {

    private final List<Tarea> tareas = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public List<Tarea> findAll() {
        return new ArrayList<>(tareas);
    }

    public Optional<Tarea> findById(Long id) {
        return tareas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    public Tarea save(Tarea t) {
        if (t.getId() == null) {
            t.setId(contadorId.getAndIncrement());
            tareas.add(t);
        } else {
            deleteById(t.getId());
            tareas.add(t);
        }
        return t;
    }

    public boolean deleteById(Long id) {
        return tareas.removeIf(t -> t.getId().equals(id));
    }
}