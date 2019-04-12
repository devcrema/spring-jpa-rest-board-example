package devcrema.spring_jpa_rest_board_example.controller;

import devcrema.spring_jpa_rest_board_example.ResponseMessage;
import devcrema.spring_jpa_rest_board_example.post.*;
import devcrema.spring_jpa_rest_board_example.user.User;
import devcrema.spring_jpa_rest_board_example.user.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private PostRepository postRepository;
    private SavePostService savePostService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity getPosts() {
        return new ResponseEntity<>(postRepository.findAllByEnabledTrue(), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable("postId") long postId) {
        Optional<GetPostProjection> optionalPost = postRepository.findById(postId);
        return optionalPost.<ResponseEntity>map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public ResponseEntity createPost(@Valid @RequestBody SavePostRequest savePostRequest, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(new ResponseMessage(errorMessage), HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userRepository.findById(((User) authentication.getPrincipal()).getId());
        if (!optionalUser.isPresent()) return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        savePostService.savePost(savePostRequest.toPost(modelMapper, optionalUser.get()));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity modifyPost(@Valid @RequestBody SavePostRequest savePostRequest, BindingResult bindingResult, @PathVariable("postId") Post post, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(new ResponseMessage(errorMessage), HttpStatus.BAD_REQUEST);
        }
        if (!post.checkAuthorOfPost((User) authentication.getPrincipal())) {
            return new ResponseEntity<>(new ResponseMessage("수정할 권한이 없습니다."), HttpStatus.FORBIDDEN);
        }
        savePostService.savePost(savePostRequest.updatePost(modelMapper, post));
        return new ResponseEntity(HttpStatus.OK);
    }
}
