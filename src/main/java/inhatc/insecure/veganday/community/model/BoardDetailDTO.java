package inhatc.insecure.veganday.community.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDetailDTO {
    private List<Board> board;
    private List<Comment> comments;

    @Builder
    public BoardDetailDTO(final List<Board> board, List<Comment> comments){
        this.board = board;
        this.comments = comments;
    }

    public static BoardDetailDTO res(final List<Board> board, List<Comment> comments) {
        return BoardDetailDTO.builder()
                .board(board)
                .comments(comments)
                .build();
    }
}
