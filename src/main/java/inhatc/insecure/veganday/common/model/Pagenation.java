package inhatc.insecure.veganday.common.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pagenation<T> {
    private int totalPages;
    private T content;

    @Builder
    public Pagenation(final int totalPages, final T content){
        this.totalPages = totalPages;
        this.content = content;
    }

    public static<T> Pagenation<T> res(final int totalPages, final T t) {
        return Pagenation.<T>builder()
                .content(t)
                .totalPages(totalPages)
                .build();
    }

}
