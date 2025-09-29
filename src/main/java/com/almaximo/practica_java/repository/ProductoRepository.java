package com.almaximo.practica_java.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.almaximo.practica_java.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByClaveContainingAndTipoProductoNombreContaining(String clave, String tipoNombre);

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.proveedores pp LEFT JOIN FETCH pp.proveedor WHERE p.id = :id")
    Optional<Producto> findByIdConProveedores(@Param("id") Integer id);
}