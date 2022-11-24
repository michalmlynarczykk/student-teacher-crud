package michalmlynarczyk.studentteachercrud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import michalmlynarczyk.studentteachercrud.entity.BasePerson;
import michalmlynarczyk.studentteachercrud.exception.EntityAlreadyExistsException;
import michalmlynarczyk.studentteachercrud.exception.EntityNotUpdatableException;
import michalmlynarczyk.studentteachercrud.repository.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Slf4j
public abstract class GenericService<T extends BasePerson> {
    private final GenericRepository<T> repository;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public T getById(Long personId) {
        T person = getPerson(personId);
        log.info("Fetching person with id: {}", personId);
        return person;
    }

    public Page<T> getAll(Integer page, Integer size, String sortBy) {
        log.info("Fetching all persons");
        Page<T> persons;
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            persons = repository.findAll(pageable);
        } catch (Exception e) {
            log.error("Pageable arguments not valid");
            throw new IllegalArgumentException(e);
        }

        if (!persons.hasContent()) {
            log.error("No persons available for this request");
            throw new EntityNotFoundException("No persons available for this request");
        }
        return persons;
    }

    public List<T> getAllByFirstAndLastName(String firstName, String lastName) {
        List<T> persons = repository.findAllByFirstNameAndLastName(firstName, lastName);
        if (persons.isEmpty()) {
            log.error("No persons with name {} {}", firstName, lastName);
            throw new EntityNotFoundException(
                    String.format("No persons with name %s %s", firstName, lastName));
        }
        return persons;
    }

    @Transactional
    public T save(T person) {
        if (repository.findByEmail(person.getEmail()).isPresent()) {
            log.error("Email: {} is already taken", person.getEmail());
            throw new EntityAlreadyExistsException(
                    String.format("Email %s is already taken", person.getEmail()));
        }
        log.info("Saving person: {} {} to database",
                person.getFirstName(),
                person.getLastName());
        return repository.save(person);
    }

    @Transactional
    public void delete(Long personId) {
        T person = getPerson(personId);

        log.info("Deleting person with id: {}", personId);
        repository.delete(person);
    }

    @Transactional
    public void update(JsonPatch patch, Long personId) {
        T person = getPerson(personId);
        T patchedPerson;
        try {
            patchedPerson = applyPatch(patch, person);
        } catch (Exception e) {
            log.error("Patching entity went wrong");
            throw new EntityNotUpdatableException(e);
        }
        Set<ConstraintViolation<T>> violations = validator.validate(patchedPerson);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new EntityNotUpdatableException("Error occurred: " + sb);
        }
        log.info("Person with id {} patched successfully", personId);
        repository.save(patchedPerson);
    }

    //the suppression is applied here because I'm sure that
    // I will be converting person to the same class as it was before patching
    @SuppressWarnings("unchecked")
    private T applyPatch(JsonPatch patch, T person) throws JsonPatchException {
        JsonNode patched = patch.apply(objectMapper.convertValue(person, JsonNode.class));
        return (T) objectMapper.convertValue(patched, person.getClass());
    }


    private T getPerson(Long personId) {
        return repository.findById(personId).orElseThrow(() -> {
            log.error("Person with id {} doesn't exists", personId);
            return new EntityNotFoundException(String.format("Person with id %d doesn't exists", personId));
        });
    }

}
