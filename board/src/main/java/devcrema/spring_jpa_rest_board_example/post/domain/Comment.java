package devcrema.spring_jpa_rest_board_example.post.domain;

import devcrema.spring_jpa_rest_board_example.common.BaseAuditingEntity;
import devcrema.spring_jpa_rest_board_example.user.domain.User;
import lombok.*;

import javax.persistence.*;

//TODO comment aggregate 고민

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
    @ManyToOne
    User user;
    @Column(name = "post_id")
    Long postId;
}
