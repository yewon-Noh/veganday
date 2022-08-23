package inhatc.insecure.veganday.mall.service;

import inhatc.insecure.veganday.mall.model.NaverProductDTO;
import inhatc.insecure.veganday.mall.model.NaverRequestVarDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MallService {

    @Value("${naver.id}")
    private String naverId;

    @Value("${naver.secret}")
    private String naverSecret;

    public List<NaverProductDTO> naverShopSearchAPI(NaverRequestVarDTO naverRequestVarDTO) {
        String url = "https://openapi.naver.com/";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("v1/search/shop.json")
                .queryParam("query", naverRequestVarDTO.getKeyword())
                .queryParam("display", naverRequestVarDTO.getDisplay())
                .queryParam("start", naverRequestVarDTO.getStart())
                .encode()
                .build()
                .toUri();
        log.info("uri : {}", uri);
        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity.
                get(uri)
                .header("X-Naver-Client-Id", naverId)
                .header("X-Naver-Client-Secret", naverSecret)
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        List<NaverProductDTO> naverProductDTOS = fromJSONtoNaverProduct(result.getBody());

        log.info("result = {}", naverProductDTOS);
        return naverProductDTOS;
    }

    private List<NaverProductDTO> fromJSONtoNaverProduct(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray naverProducts = rjson.getJSONArray("items");

        List<NaverProductDTO> naverProductDtoList = new ArrayList<>();
        for (int i = 0; i < naverProducts.length(); i++) {
            JSONObject naverProductsJson = (JSONObject) naverProducts.get(i);
            NaverProductDTO itemDto = new NaverProductDTO(naverProductsJson);
            naverProductDtoList.add(itemDto);
        }
        return naverProductDtoList;
    }
}
