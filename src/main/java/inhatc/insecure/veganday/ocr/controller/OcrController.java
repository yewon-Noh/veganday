package inhatc.insecure.veganday.ocr.controller;

import inhatc.insecure.veganday.common.model.ResponseFmt;
import inhatc.insecure.veganday.common.model.ResponseMessage;
import inhatc.insecure.veganday.common.model.StatusCode;
import inhatc.insecure.veganday.common.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    
    @Autowired
    FileService fileService;

    @Value("${imageServer.path}")
    private String imageServerPath;

    @PostMapping("")
    public ResponseEntity ocr(@RequestBody MultipartFile file){
        
        String url = fileService.uploadFile(file);
        if(!(url != null || url != "")){
            return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.SAVE_OCR_IMAGE_ERROR, "이미지 파일만 가능합니다."), HttpStatus.OK);
        }

        String imageUrl = imageServerPath + url;

        // OCR 수행

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.SAVE_OCR_IMAGE, imageUrl), HttpStatus.OK);
    }
}
