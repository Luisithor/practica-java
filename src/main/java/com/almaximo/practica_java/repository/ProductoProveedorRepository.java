package com.almaximo.practica_java.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.almaximo.practica_java.model.ProductoProveedor;

import jakarta.transaction.Transactional;

public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Integer> {

    @Modifying()
    @Transactional
    @Query("DELETE FROM ProductoProveedor pp WHERE pp.producto.id = :idProducto")
    void eliminarRelacionesProductoId(@Param("idProducto")Integer idProducto);

}
