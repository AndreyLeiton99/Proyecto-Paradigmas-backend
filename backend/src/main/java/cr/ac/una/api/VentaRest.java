package cr.ac.una.api;

import cr.ac.una.entity.Producto;
import cr.ac.una.entity.Venta;
import cr.ac.una.repository.ProductoRepository;
import cr.ac.una.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping(path="/venta")
public class VentaRest {
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Venta>> findAll(){
        List<Venta> list = new ArrayList<Venta>();
        ventaRepository.findAll().forEach(e->list.add(e));
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600) // create
    public ResponseEntity<Venta> create(@RequestBody Venta venta){
        AtomicBoolean invalid = new AtomicBoolean(false); // informa si se incumple con la regla de negocio
        List<Producto> list = new ArrayList<Producto>();
        productoRepository.findAll().forEach(e->{
            list.add(e);
            if(e.getId_Producto().equals(venta.getProducto().getId_Producto())){
                venta.getProducto().setDescripcion( e.getDescripcion() );
                venta.getProducto().setCantidad( e.getCantidad() );
            }
        });
        // lo anterior es porque llega con "cantidad = null", eso al hacer debugging en Postman si llega con los datos completos

        Integer cantProducto = venta.getProducto().getCantidad(); // obtiene la cantidad actual del producto
        Integer cantSolicitada = venta.getCantidad(); // guarda la cantidad solicitada por el usuario

        if(cantProducto <= 0 || cantSolicitada > cantProducto) //si la cantidad de producto es menor o igual a cero,
        {                                                       // o si la cantidad solicitada excede la cantidad disponible
            invalid.set(true); // bad request, va en contra de la regla de negocio
        }

        if(invalid.get()){
            return ResponseEntity.badRequest().build();
        }else{
            venta.getProducto().setCantidad(cantProducto - cantSolicitada); // actualiza la cantidad del producto
            productoRepository.save(venta.getProducto()); // actualiza el producto en el repository
            return ResponseEntity.ok(ventaRepository.save(venta)); // guarda la venta
        }

    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Venta> findById(@PathVariable Long id) {
        Optional<Venta> venta = ventaRepository.findById(id);
        if (!venta.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(venta.get());
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Venta> update(@PathVariable Long id, @RequestBody Venta venta) {
        if (!ventaRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        venta.setId_Venta(id);
        return ResponseEntity.ok(ventaRepository.save(venta));
    }


    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable Long id) {
        if (!ventaRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        ventaRepository.delete(ventaRepository.findById(id).get());
        return ResponseEntity.ok().build();
    }
}
