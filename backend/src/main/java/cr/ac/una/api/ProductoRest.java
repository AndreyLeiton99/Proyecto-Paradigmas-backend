package cr.ac.una.api;

import cr.ac.una.entity.Producto;
import cr.ac.una.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping(path="/productos")
public class ProductoRest {
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Producto>> findAll(){
        List<Producto> list = new ArrayList<Producto>();
        productoRepository.findAll().forEach(e->list.add(e));
        return ResponseEntity.ok(list);
    }


    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600) // create
    public ResponseEntity<Producto> create(@RequestBody Producto producto){
        AtomicBoolean repeated = new AtomicBoolean(false);
        List<Producto> list = new ArrayList<Producto>(); // lista donde se guardan los productos temporalmente
        productoRepository.findAll().forEach(e->{ // validacion de producto unico a insertar
            list.add(e);
            if(e.getDescripcion().equals(producto.getDescripcion())){ // si el producto ya existe, BAD REQUEST
                //ResponseEntity.badRequest().build();
                repeated.set(true);
            }
        });

        if(repeated.get()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Producto> findById(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (!producto.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(producto.get());
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto producto) {
        if (!productoRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        producto.setId_Producto(id);
        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable Long id) {
        if (!productoRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        productoRepository.delete(productoRepository.findById(id).get());
        return ResponseEntity.ok().build();
    }

}
