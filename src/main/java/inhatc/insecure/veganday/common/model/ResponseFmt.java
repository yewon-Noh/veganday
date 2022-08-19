package inhatc.insecure.veganday.common.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseFmt<T> {
    private int statusCode;
    private String responseMessage;
    private T data;

    @Builder
    public ResponseFmt(final int statusCode, final String responseMessage, final T data) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    @Builder
    public ResponseFmt(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

    public static<T> ResponseFmt<T> res(final int statusCode, final String responseMessage, final T t) {
        return ResponseFmt.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }

    public static<T> ResponseFmt<T> res(final int statusCode, final String responseMessage) {
        return ResponseFmt.<T>builder()
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }
}
