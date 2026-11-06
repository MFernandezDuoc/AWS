package com.DMM.MDM.service;

import com.DMM.MDM.model.Producto;
import com.DMM.MDM.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> obtenerTodos() {
        return repository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public Producto crear(Producto producto) {
        validarProducto(producto);
        producto.setId(null);
        return repository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoDetalles) {
        Producto existente = obtenerPorId(id);
        validarProducto(productoDetalles);

        existente.setNombre(productoDetalles.getNombre());
        existente.setPrecio(productoDetalles.getPrecio());
        existente.setStock(productoDetalles.getStock());

        return repository.save(existente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        repository.deleteById(id);
    }

    private void validarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}