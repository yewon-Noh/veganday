package inhatc.insecure.veganday.community.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardUploadDTO {
    private String title;
    private String cn;
    private String userName;

    private MultipartFile file;
}
