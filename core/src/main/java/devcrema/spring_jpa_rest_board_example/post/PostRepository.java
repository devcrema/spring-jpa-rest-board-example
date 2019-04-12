package devcrema.spring_jpa_rest_board_example.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<GetPostProjection> findAllByEnabledTrue();
    Optional<GetPostProjection> findById(long id);
}
