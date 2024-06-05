package ai.verse.repo;



import ai.verse.repo.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PostRepository extends JpaRepository<PostEntity, Long> {


    // Custom Query - Get rows where sentiment is null. NULL is different from blank
    @Query("SELECT u FROM PostEntity u WHERE u.sentiment is null")  // jpa Query Language
        List<PostEntity> findRowsWithNoSentiment();

    //   select * from posts where sentiment is null;  mysql SQL
    //   select u from PostEntity u where u.sentiment is null --> JPA Query Language

  //mysql   select * from posts where sentiment is null;


}