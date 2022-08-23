package inhatc.insecure.veganday.mall.controller;

import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;
import inhatc.insecure.veganday.mall.model.NaverProductDTO;
import inhatc.insecure.veganday.mall.model.NaverRequestVarDTO;
import inhatc.insecure.veganday.mall.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mall")
public class MallControler {

    @Autowired
    MallService mallService;

    @GetMapping("")
    public ResponseEntity mall(@RequestParam(required = false, defaultValue = "비건음식") String keyword
                    , @RequestParam(required = false, defaultValue = "50") Integer display
                    , @RequestParam(required = false, defaultValue = "1") Integer start){
        NaverRequestVarDTO varDTO = NaverRequestVarDTO.builder()
                .keyword(keyword)
                .display(display)
                .start(start)
                .build();

        List<NaverProductDTO> list = mallService.naverShopSearchAPI(varDTO);

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.READ_MALL, list), HttpStatus.OK);
    }
}
