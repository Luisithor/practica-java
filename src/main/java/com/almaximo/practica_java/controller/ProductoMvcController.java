package com.almaximo.practica_java.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
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
import com.almaximo.practica_java.model.ProductoProveedor;
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
            @PageableDefault(size = 10)
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
        model.addAttribute("nuevoProdProv", new ProductoProveedor());

        return "formulario.html";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Producto producto = productoService.obtenerPorId(id)
        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        System.out.println("Proveedores del producto: " + producto.getProveedores());

        List<TipoProducto> tipos = tipoProductoRepository.findAll();
        List<Proveedor> proveedores = proveedorRepository.findAll();

        model.addAttribute("producto", producto);
        model.addAttribute("tipos", tipos);
        model.addAttribute("proveedores", proveedores);
        model.addAttribute("nuevoProdProv", new ProductoProveedor());

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

    @PostMapping("/agregarProveedor")
    public String agregarProductoProveedor(@ModelAttribute("nuevoProdProv") ProductoProveedor nuevoProdProv) {
        
        Integer productoId = nuevoProdProv.getProducto().getId();
        Producto producto = productoService.obtenerPorId(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Proveedor proveedor = proveedorRepository.findById(nuevoProdProv.getProveedor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));

        nuevoProdProv.setProducto(producto);
        nuevoProdProv.setProveedor(proveedor);

        productoService.guardarProductoProveedor(nuevoProdProv);

        return "redirect:/productos/editar/" + productoId;
    }

    @GetMapping("/eliminarProveedor/{id}")
    public String eliminarProductoProveedor(@PathVariable Integer id) {

        Optional<ProductoProveedor> optionalPp = productoService.obtenerProductoProveedorPorId(id);

        if (optionalPp.isPresent()) {
            ProductoProveedor pp = optionalPp.get();
            Integer productoId = pp.getProducto().getId();

            productoService.eliminarProductoProveedor(id);


            return "redirect:/productos/editar/" + productoId;
        } 

        return "redirect:/productos";
    }
}
