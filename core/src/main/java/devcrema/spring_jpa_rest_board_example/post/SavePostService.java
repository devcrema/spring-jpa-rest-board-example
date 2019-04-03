package devcrema.spring_jpa_rest_board_example.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavePostService {
    private final PostRepository postRepository;

    @Transactional
    public void savePost(Post post){
        postRepository.save(post);
    }


}
