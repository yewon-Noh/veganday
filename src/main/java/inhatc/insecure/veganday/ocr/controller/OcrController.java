package inhatc.insecure.veganday.ocr.controller;

import inhatc.insecure.veganday.common.model.FileDTO;
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
    public ResponseEntity ocr(@RequestBody(required = false) MultipartFile file){

        if(file == null){
            return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.DONT_SEND_PARAM), HttpStatus.OK);
        }

        FileDTO filedto = fileService.uploadFile(file);

        if(filedto.getCode() == -2){
            return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.IMAGE_UPLOAD_ERROR), HttpStatus.OK);
        }
        if(filedto.getCode() == -1){
            return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.CANT_NOT_OTHER_FILES), HttpStatus.OK);
        }

        String imageUrl = filedto.getFilepath();

        // OCR 수행

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.SAVE_OCR_IMAGE, imageUrl), HttpStatus.OK);
    }
}
