package inhatc.insecure.veganday.common.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDTO {
    private String filename;
    private String filepath;
    private int code;

    @Builder
    public FileDTO(final String filename, String filepath, int code){
        this.filename = filename;
        this.filepath = filepath;
        this.code = code;
    }

    public static FileDTO res(final String filename, String filepath, int code) {
        return FileDTO.builder()
                .filename(filename)
                .filepath(filepath)
                .code(code)
                .build();
    }
}
