package devcrema.spring_jpa_rest_board_example.post.domain;

import devcrema.spring_jpa_rest_board_example.post.domain.Comment;
import devcrema.spring_jpa_rest_board_example.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 5000)
    private String content;

    @Setter
    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "post_id")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    private boolean enabled = true;

    public boolean checkAuthorOfPost(User user){
        return Objects.equals(this.user.getId(), user.getId());
    }

    public void addComment(Comment comment) {
        if(comments == null) comments = new ArrayList<>();
        comments.add(comment);
    }
}
