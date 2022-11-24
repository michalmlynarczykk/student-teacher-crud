package michalmlynarczyk.studentteachercrud.repository;

import michalmlynarczyk.studentteachercrud.entity.BasePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T extends BasePerson> extends JpaRepository<T, Long> {
    Optional<T> findByEmail(String email);

    List<T> findAllByFirstNameAndLastName(String firstName, String LastName);
}
