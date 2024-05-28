package ai.verse.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface AadhaarRepository extends JpaRepository<AadhaarEntity, Long> {




}