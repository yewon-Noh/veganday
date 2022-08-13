package inhatc.insecure.veganday.community.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @NotNull
    private String ttl;

    @NotNull
    private String cn;

    private String userId;

    private long hit;

    private String writeDt;
}
