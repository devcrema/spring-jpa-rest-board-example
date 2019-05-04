package devcrema.spring_jpa_rest_board_example.post;

import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post implements GetPostProjection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50)
    private String title;

    @Column(length = 5000)
    private String content;

    @Setter
    @ManyToOne
    @PrimaryKeyJoinColumn
    User user;

    @Builder.Default
    private boolean enabled = true;

    public boolean checkAuthorOfPost(User user){
        return Objects.equals(this.user.getId(), user.getId());
    }
}
