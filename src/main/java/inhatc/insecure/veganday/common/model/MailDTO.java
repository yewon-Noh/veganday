package inhatc.insecure.veganday.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDTO {
    private String email;
    private String title;
    private String content;

    @Builder
    public MailDTO(final String email, String title, String content){
        this.email = email;
        this.title = title;
        this.content = content;
    }
}
