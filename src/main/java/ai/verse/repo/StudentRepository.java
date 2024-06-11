package ai.verse.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface StudentRepository extends JpaRepository<Studententity, Long> {


}