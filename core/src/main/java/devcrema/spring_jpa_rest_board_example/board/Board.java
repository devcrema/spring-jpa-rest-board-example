package devcrema.spring_jpa_rest_board_example.board;

import devcrema.spring_jpa_rest_board_example.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50)
    private String title;

    @Column(length = 5000)
    private String content;

    @ManyToOne
    @PrimaryKeyJoinColumn
    User user;

    //TODO 기능 구현하면서 viewCount, Like, Images 등 추가할 것!
}
