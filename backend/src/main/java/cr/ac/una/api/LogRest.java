package cr.ac.una.api;


import cr.ac.una.entity.Log;
import cr.ac.una.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/logs")
public class LogRest {
    @Autowired
    private LogRepository logRepository;

    @GetMapping
    @CrossOrigin(origins = "*", maxAge = 3600) // create
    public ResponseEntity<List<Log>> findAll(){
        List<Log> list = new ArrayList<Log>();
        logRepository.findAll().forEach(e->list.add(e));
        return ResponseEntity.ok(list);
    }
}
