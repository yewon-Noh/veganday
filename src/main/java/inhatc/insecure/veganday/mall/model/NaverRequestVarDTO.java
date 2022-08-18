package inhatc.insecure.veganday.mall.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NaverRequestVarDTO {
    private String keyword;
    private Integer display;
    private Integer start;
}
