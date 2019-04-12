package devcrema.spring_jpa_rest_board_example;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@ToString
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditingEntity {
    @CreatedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedTime;
}
