package inhatc.insecure.veganday.community.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inhatc.insecure.veganday.community.model.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
public class Attachfile {
    @Id
    private Long fid;

    private String filename;

    private String filepath;

    @OneToOne
    @JoinColumn(name = "bid")
    @JsonIgnore
    private Board board;
}
