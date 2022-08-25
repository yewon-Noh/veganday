package inhatc.insecure.veganday.ocr.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;

@RestController
@RequestMapping("/classify")
public class OcrClassifyController {
	
	String result;
    List<String> list = new ArrayList<>(Arrays.asList("쇠고기", "가금류", "꿀","밀납", "가쓰오부시", "멸치", "연어", "연육", "피쉬콜라겐", "코치닐", "카민", "카르민", "적색색소 4호", "쉘락", "우지", "조미쇠고기맛후레크", "돼지고기", "닭고기", "버터", "가공버터", "우유", "달걀"));
	@PostMapping("")
	ResponseEntity classify(@RequestBody(required = false) ArrayList<String> info) throws GeneralSecurityException, IOException, JSONException{
		result = "";
		info.forEach(element -> {
			for (String str : list) {
				if (element.equals(str)) {
					result += element + ',';           
				}        
			}
		});
		if (result.endsWith(",")) {
			result =  result.substring(0, result.length() - 1);        
		}
        return new ResponseEntity<ResponseFmt<String>>(ResponseFmt.res(StatusCode.OK, ResponseMessage.CLASSIFY_SUCCESS, result), HttpStatus.OK);
    }
}
