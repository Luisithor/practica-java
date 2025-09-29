package com.almaximo.practica_java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almaximo.practica_java.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
List<Producto> findByClaveContainingAndTipoProductoNombreContaining(String clave, String tipoNombre);

}