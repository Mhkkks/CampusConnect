package ai.verse.repo;



import ai.verse.repo.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PostRepository extends JpaRepository<PostEntity, Long> {

//    // it works if it matches the book field name
//    List<PostEntity> findByTitle(String title);
//
//    // Custom Query
//    @Query("SELECT b FROM Book b WHERE b.publishDate > :date")
//    List<Book> findByPublishedDateAfter(@Param("date") LocalDate date);

}