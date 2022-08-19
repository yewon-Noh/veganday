package inhatc.insecure.veganday.community.model;

import com.google.gson.annotations.Expose;
import inhatc.insecure.veganday.common.model.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
public class Board extends BaseTimeEntity {
    @Id
    private Long bid;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 50, message = "제목은 최대 50자 입니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 1, max = 500, message = "내용은 최대 500자 입니다.")
    private String cn;

    private String userId;

    @ColumnDefault("0")
    private long hit;

    @Formula("(SELECT count(1) FROM comment c WHERE c.bid = bid)")
    private long comment;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "board")
    private Attachfile attachfile;
}
