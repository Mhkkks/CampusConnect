package ai.verse.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface AQIRepository extends JpaRepository<AQIEntity, Long> {



   // @Query("SELECT u FROM AQIEntity as u where u.id> :num")  // jpa Query Language

    List<AQIEntity> findAllByIdGreaterThan(Long aLong);
}