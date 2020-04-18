package devcrema.spring_jpa_rest_board_example.post.presentation;

import devcrema.spring_jpa_rest_board_example.post.domain.Post;
import devcrema.spring_jpa_rest_board_example.user.domain.User;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SavePostRequest {
    @NotNull(message = "제목을 입력하셔야합니다.")
    @Size(min = 1, max = 50, message = "제목은 50글자 안으로 입력하셔야합니다.")
    private String title;
    @NotNull(message = "내용을 입력하셔야합니다.")
    @Size(min = 1, max = 5000, message = "내용은 5000자 안으로 입력하셔야합니다.")
    private String content;

    public Post toPost(ModelMapper modelMapper, User user) {
        Post post = modelMapper.map(this, Post.class);
        post.setUser(user);
        return post;
    }

    public Post updatePost(ModelMapper modelMapper, Post post){
        modelMapper.map(this, post);
        return post;
    }
}
