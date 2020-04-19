package devcrema.spring_jpa_rest_board_example.post.application;

import devcrema.spring_jpa_rest_board_example.post.domain.Post;
import devcrema.spring_jpa_rest_board_example.post.query.GetPostRepository;
import devcrema.spring_jpa_rest_board_example.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavePostService {
    private final GetPostRepository postRepository;

    @Transactional
    public void savePost(Post post){
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Post post, User user){
        if (!post.checkAuthorOfPost(user)) throw new NotAuthorOfPostException("수정할 권한이 없습니다.");
        postRepository.save(post);
    }

}
