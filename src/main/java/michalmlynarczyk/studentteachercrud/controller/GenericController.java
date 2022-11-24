package michalmlynarczyk.studentteachercrud.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import michalmlynarczyk.studentteachercrud.entity.BasePerson;
import michalmlynarczyk.studentteachercrud.service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public abstract class GenericController<T extends BasePerson> {
    private final GenericService<T> service;

    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable("id") Long personId) {
        return ResponseEntity.ok().body(service.getById(personId));
    }

    @GetMapping
    ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping(params = {"first-name", "last-name"})
    ResponseEntity<Object> getAllByFirstAndLastName(
            @RequestParam("first-name") String firstName,
            @RequestParam("last-name") String lastName) {
        List<T> persons = service.getAllByFirstAndLastName(firstName, lastName);
        HashMap<String, Object> response = new HashMap<>();
        response.put("firstName", firstName);
        response.put("lastName", lastName);
        response.put("persons", persons);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    ResponseEntity<Object> save(@Valid @RequestBody T person) {
        T savedPerson = service.save(person);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest()
                .toUriString() + "/" + savedPerson.getId());
        return ResponseEntity
                .created(uri)
                .body(savedPerson);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable(value = "id") Long personId) {
        service.delete(personId);
        return ResponseEntity
                .ok(String.format("Person with id %d deleted successfully", personId));
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json-patch+json"})
    public ResponseEntity<Object> update(@PathVariable("id") Long personId, @RequestBody JsonPatch patch) {
        service.update(patch, personId);
        return ResponseEntity
                .ok(String.format("Person with id %d updated successfully", personId));
    }

}
