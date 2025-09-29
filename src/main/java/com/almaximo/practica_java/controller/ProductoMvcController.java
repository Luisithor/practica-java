package com.almaximo.practica_java.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.almaximo.practica_java.service.ProductoService;
import org.springframework.ui.Model;
import com.almaximo.practica_java.model.Producto;
import com.almaximo.practica_java.model.Proveedor;
import com.almaximo.practica_java.model.TipoProducto;
import com.almaximo.practica_java.repository.ProveedorRepository;
import com.almaximo.practica_java.repository.TipoProductoRepository;

@Controller
@RequestMapping("/productos")
public class ProductoMvcController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public String listarProductos(
            @RequestParam(required = false, defaultValue = "") String clave,
            @RequestParam(required = false, defaultValue = "") String tipoProducto,
            Model model) {

        List<Producto> productos = productoService.buscarProductos(clave, tipoProducto);
        List<TipoProducto> tipos = tipoProductoRepository.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("tipos", tipos);
        model.addAttribute("clave", clave);
        model.addAttribute("tipoProducto", tipoProducto);

        return "listado.html";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Producto producto = new Producto();
        List<TipoProducto> tipos = tipoProductoRepository.findAll();
        List<Proveedor> proveedores = proveedorRepository.findAll();

        model.addAttribute("producto", producto);
        model.addAttribute("tipos", tipos);
        model.addAttribute("proveedores", proveedores);

        return "formulario.html";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Producto producto = productoService.obtenerPorId(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        List<TipoProducto> tipos = tipoProductoRepository.findAll();
        List<Proveedor> proveedores = proveedorRepository.findAll();

        model.addAttribute("producto", producto);
        model.addAttribute("tipos", tipos);
        model.addAttribute("proveedores", proveedores);

        return "formulario.html";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos";
    }
}
