package inhatc.insecure.veganday.mall.model;

import lombok.Getter;
import org.json.JSONObject;

@Getter
public class NaverProductDTO {
    private String productId;
    private String title;
    private String link;
    private String image;
    private Integer lprice;
    private String mallName;

    public NaverProductDTO(JSONObject item) {
        this.productId = item.getString("productId");
        this.title = item.getString("title");
        this.link = item.getString("link");
        this.image = item.getString("image");
        this.lprice = item.getInt("lprice");
        this.mallName = item.getString("mallName");
    }
}
