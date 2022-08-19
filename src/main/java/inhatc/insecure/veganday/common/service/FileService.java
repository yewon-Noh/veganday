package inhatc.insecure.veganday.common.service;

import inhatc.insecure.veganday.common.model.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class FileService {

    @Value("${imageFile.uploadPath}")
    private String filePath;

    final List<String> allowFileType = Arrays.asList(".jpg", ".jpeg", ".png");

    @Value("${imageServer.path}")
    private String imageServerPath;

    public FileDTO uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        long size = file.getSize();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."), fileName.length());

        // 확장자 검사
        int typeChk = 0;
        for (String type:allowFileType) {
            if(fileExtension.equals(type))
                typeChk += 1;
        }

        if(typeChk < 1) {
            return new FileDTO("","",-1);
        }

        // 저장할 위치 지정
        Calendar cal = Calendar.getInstance();
        String year = Integer.toString(cal.get(Calendar.YEAR));
        String month = String.format("%02d", (cal.get(Calendar.MONTH) + 1));
        String day = String.format("%02d", (cal.get(Calendar.DATE)));
        String uploadPath = "/" + year + "/" + month + "/" + day + "/";

        File dir = new File(filePath, uploadPath);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        String newFileName = Long.toString(cal.getTimeInMillis()) + fileExtension;
        String newFilePath = uploadPath + newFileName;

        File saveFile = new File(filePath + newFilePath);
        try {
            file.transferTo(saveFile);
        } catch (IllegalStateException e) {
            log.error("파일 업로드 에러");
            return new FileDTO("","",-2);
        } catch (IOException e) {
            log.error("파일 업로드 에러");
            return new FileDTO("","",-2);
        }

        return new FileDTO(fileName, imageServerPath + newFilePath , 1);
    }
}
