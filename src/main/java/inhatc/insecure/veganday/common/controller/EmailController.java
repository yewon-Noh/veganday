package inhatc.insecure.veganday.common.controller;

import inhatc.insecure.veganday.common.model.*;
import inhatc.insecure.veganday.common.service.EmailService;
import inhatc.insecure.veganday.common.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    FileService fileService;

    @Autowired
    EmailService emailService;

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity emailSend(MultipartHttpServletRequest request, @RequestParam(value = "file") MultipartFile file) throws MessagingException, IOException {

        if(file != null) {
            FileDTO filedto = fileService.uploadFile(file);

            if (filedto.getCode() == -2) {
                return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.IMAGE_UPLOAD_ERROR), HttpStatus.OK);
            }
            if (filedto.getCode() == -1) {
                return new ResponseEntity(ResponseFmt.res(StatusCode.BAD_REQUEST, ResponseMessage.CANT_NOT_OTHER_FILES), HttpStatus.OK);
            }

            MailDTO mail = MailDTO.builder()
                    .email(request.getParameter("email"))
                    .title("[비건데이] 화면 캡처 결과 전송")
                    .content("비건데이 화면 캡처본입니다.")
                    .build();

            emailService.sendMail(mail, filedto);
        }

        return new ResponseEntity(ResponseFmt.res(StatusCode.OK, ResponseMessage.SEND_EMAIL_SUCCESS), HttpStatus.OK);
    }

}
