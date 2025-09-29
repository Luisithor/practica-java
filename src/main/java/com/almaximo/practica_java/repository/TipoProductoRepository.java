package com.almaximo.practica_java.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.almaximo.practica_java.model.TipoProducto;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Integer> {

    
}
