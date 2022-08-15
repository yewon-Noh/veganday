package inhatc.insecure.veganday.community.model;

import java.time.LocalDateTime;

public interface BoardDTO {
    Long getBid();
    String getTitle();
    String getUserId();
    long getHit();
    long getCommnet();
    LocalDateTime getWriteDt();
}
