package devcrema.spring_jpa_rest_board_example.controller;

import devcrema.spring_jpa_rest_board_example.exception.ResourceNotFoundException;
import devcrema.spring_jpa_rest_board_example.post.*;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final SavePostService savePostService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<GetPostResponse> getPosts() {
        return postRepository.findAllByEnabledTrue();
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public GetPostResponse getPost(@PathVariable("postId") long postId) {
        return postRepository.getById(postId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createPost(@Valid @RequestBody SavePostRequest savePostRequest, User user) {
        //TODO validation test
        savePostService.savePost(savePostRequest.toPost(modelMapper, user));
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyPost(@Valid @RequestBody SavePostRequest savePostRequest
            , @PathVariable("postId") Long postId
            , User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("해당 게시물을 찾을 수 없습니다."));
        savePostService.updatePost(savePostRequest.updatePost(modelMapper, post), user);
    }
}
