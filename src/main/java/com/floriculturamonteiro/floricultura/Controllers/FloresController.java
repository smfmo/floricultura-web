package com.floriculturamonteiro.floricultura.Controllers;

import com.floriculturamonteiro.floricultura.dtos.FloresDto;
import com.floriculturamonteiro.floricultura.model.Flores;
import com.floriculturamonteiro.floricultura.repositories.FloresRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flores")
public class FloresController {

    private final FloresRepository repository;

    @Autowired
    public FloresController(FloresRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Flores>> getAll(){
        List<Flores> listFlores = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listFlores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        Optional<Flores> Flores = repository.findById(id);
        if (Flores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flores not found");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(Flores.get());
    }

    @PostMapping
    public ResponseEntity<Flores> save(@RequestBody FloresDto dto) {
        var flores = new Flores();
        BeanUtils.copyProperties(dto, flores);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(flores));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        Optional<Flores> Flores = repository.findById(id);
        if (Flores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flores not found");
        }
        repository.delete(Flores.get());
        return ResponseEntity.status(HttpStatus.OK).body("Flores deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody FloresDto dto){
        Optional<Flores> Flores = repository.findById(id);
        if (Flores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flores not found");
        }
        var floresModel = Flores.get();
        BeanUtils.copyProperties(dto, floresModel);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(floresModel));
    }
}
