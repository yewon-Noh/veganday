package inhatc.insecure.veganday.community.model;

import com.sun.istack.NotNull;
import inhatc.insecure.veganday.common.model.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @NotNull
    private String title;

    @NotNull
    private String cn;

    private String userId;

    @ColumnDefault("0")
    private long hit;
}
