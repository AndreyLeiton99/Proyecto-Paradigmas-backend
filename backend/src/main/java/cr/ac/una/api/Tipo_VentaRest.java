package cr.ac.una.api;

import cr.ac.una.entity.Producto;
import cr.ac.una.entity.Tipo_Venta;
import cr.ac.una.repository.Tipo_VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping(path="/tienda")
public class Tipo_VentaRest {
    @Autowired
    private Tipo_VentaRepository tipo_VentaRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Tipo_Venta>> findAll(){
        List<Tipo_Venta> list = new ArrayList<Tipo_Venta>();
        tipo_VentaRepository.findAll().forEach(e->list.add(e));
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600) // create
    public ResponseEntity<Tipo_Venta> create(@RequestBody Tipo_Venta tipo_Venta){
        AtomicBoolean repeated = new AtomicBoolean(false);
        List<Tipo_Venta> list = new ArrayList<Tipo_Venta>(); // lista donde se guardan los tipos de venta temporalmente
        tipo_VentaRepository.findAll().forEach(e->{ // validacion de tipo de venta unico a agregar
            list.add(e);
            if(e.getDescripcion().equals(tipo_Venta.getDescripcion())){ // si el tipo ya existe, BAD REQUEST
                //ResponseEntity.badRequest().build();
                repeated.set(true);
            }
        });

        if(repeated.get()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tipo_VentaRepository.save(tipo_Venta));
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Tipo_Venta> findById(@PathVariable Long id) {
        Optional<Tipo_Venta> tipo_Venta = tipo_VentaRepository.findById(id);
        if (!tipo_Venta.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tipo_Venta.get());
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Tipo_Venta> update(@PathVariable Long id, @RequestBody Tipo_Venta tipo_venta) {
        if (!tipo_VentaRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        tipo_venta.setId_tipo_venta(id);
        return ResponseEntity.ok(tipo_VentaRepository.save(tipo_venta));
    }


    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable Long id) {
        if (!tipo_VentaRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        tipo_VentaRepository.delete(tipo_VentaRepository.findById(id).get());
        return ResponseEntity.ok().build();
    }
}
