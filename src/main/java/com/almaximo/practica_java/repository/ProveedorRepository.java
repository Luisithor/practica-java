package com.almaximo.practica_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.almaximo.practica_java.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

}
