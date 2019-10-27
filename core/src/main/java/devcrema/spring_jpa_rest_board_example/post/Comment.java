package devcrema.spring_jpa_rest_board_example.post;

import devcrema.spring_jpa_rest_board_example.BaseAuditingEntity;
import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String content;
    User user;
    @Column(name = "post_id")
    Long postId;
}
