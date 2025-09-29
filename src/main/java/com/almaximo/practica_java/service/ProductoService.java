package com.almaximo.practica_java.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.almaximo.practica_java.model.Producto;
import com.almaximo.practica_java.model.ProductoProveedor; 
import com.almaximo.practica_java.repository.ProductoRepository;
import com.almaximo.practica_java.repository.ProductoProveedorRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoService {

    @Autowired
    private final ProductoProveedorRepository productoProveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;

    ProductoService(ProductoProveedorRepository productoProveedorRepository) {
        this.productoProveedorRepository = productoProveedorRepository;
    }

    public List<Producto> buscarProductos(String clave, String tipoNombre) {
        return productoRepository.findByClaveContainingAndTipoProductoNombreContaining(clave, tipoNombre);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    public Optional<Producto> obtenerPorId(Integer id) {
        return productoRepository.findById(id);
    }
}
