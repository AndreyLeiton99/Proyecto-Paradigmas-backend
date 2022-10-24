package cr.ac.una.api;

import cr.ac.una.entity.Persona;
import cr.ac.una.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping(path="/clientes")
public class PersonaRest {
    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<Persona>> findAll(){
        List<Persona> list = new ArrayList<Persona>();
        personaRepository.findAll().forEach(e->list.add(e));
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600) // create
    public ResponseEntity<Persona> create(@RequestBody Persona persona){
        AtomicBoolean repeated = new AtomicBoolean(false);
        List<Persona> list = new ArrayList<Persona>(); // lista donde se guardan las personas temporalmente
        personaRepository.findAll().forEach(e->{ // validacion de usuario unico a insertar
            list.add(e);
            if(e.getIdentificacion().equals(persona.getIdentificacion())){ // si el usuario ya existe, BAD REQUEST
                //ResponseEntity.badRequest().build();
                repeated.set(true);
            }
        });

        if(repeated.get()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personaRepository.save(persona)); // si no, se agrega
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Persona> findById(@PathVariable Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (!persona.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(persona.get());
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Persona> update(@PathVariable Long id, @RequestBody Persona persona) {
        if (!personaRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        persona.setId_Persona(id);
        return ResponseEntity.ok(personaRepository.save(persona));
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity delete(@PathVariable Long id) {
        if (!personaRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        personaRepository.delete(personaRepository.findById(id).get());
        return ResponseEntity.ok().build();
    }



}
