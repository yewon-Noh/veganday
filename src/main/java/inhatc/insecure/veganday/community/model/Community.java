package inhatc.insecure.veganday.community.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board")
@Entity
public class Community {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "board_ttl")
    private String ttl;

    @NotNull
    @Column(name = "board_cn")
    private String cn;

    @Column(name = "board_user_id")
    private String userId;

    @Column(name = "board_hit_ct")
    private long hit;

    @Column(name = "board_write_dt")
    private String writeDt;
}
