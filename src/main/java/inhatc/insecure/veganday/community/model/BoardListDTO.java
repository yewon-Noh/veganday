package inhatc.insecure.veganday.community.model;

import inhatc.insecure.veganday.common.model.Pagenation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardListDTO {
    private Long bid;
    private String title;
    private String userId;
    private long hit;
    private long comment;
    private String writeDt;

    @Builder
    public BoardListDTO(final long bid, String title, String userId, long hit, long comment, String writeDt){
        this.bid = bid;
        this.title = title;
        this.userId = userId;
        this.hit = hit;
        this.comment = comment;
        this.writeDt = writeDt;
    }

    public static BoardListDTO res(final long bid, String title, String userId, long hit, long comment, String writeDt) {
        return BoardListDTO.builder()
                .bid(bid)
                .title(title)
                .userId(userId)
                .hit(hit)
                .comment(comment)
                .writeDt(writeDt)
                .build();
    }
}
