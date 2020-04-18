package devcrema.spring_jpa_rest_board_example.post.query;

import devcrema.spring_jpa_rest_board_example.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GetPostRepository extends JpaRepository<Post, Long> {
    List<GetPostResponse> findAllByEnabledTrue();
    Optional<GetPostResponse> getById(long id);
    Optional<GetPostDto> getDtoById(long id);
}
