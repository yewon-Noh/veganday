package inhatc.insecure.veganday.common.service;

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

    public String uploadFile(MultipartFile file) {
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
            return "";
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

            // DB 저장

        } catch (IllegalStateException e) {
            // 보안에 안좋음 - 수정 필요
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/image" + newFilePath;
    }
}
