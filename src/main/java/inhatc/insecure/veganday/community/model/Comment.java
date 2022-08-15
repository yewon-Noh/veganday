package inhatc.insecure.veganday.community.model;

import com.sun.istack.NotNull;
import inhatc.insecure.veganday.common.model.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    private Long cid;

    private Long bid;

    @NotNull
    private String cm;

    private String userId;
}
