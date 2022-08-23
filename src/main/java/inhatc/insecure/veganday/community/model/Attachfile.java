package inhatc.insecure.veganday.community.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inhatc.insecure.veganday.community.model.Board;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
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

    @Builder
    public Attachfile(final long fid, String filename, String filepath, Board board){
        this.fid = fid;
        this.filename = filename;
        this.filepath = filepath;
        this.board = board;
    }
}
