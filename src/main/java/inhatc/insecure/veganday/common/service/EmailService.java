package inhatc.insecure.veganday.common.service;

import inhatc.insecure.veganday.common.model.FileDTO;
import inhatc.insecure.veganday.common.model.MailDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${imageFile.uploadPath}")
    private String filePath;

    @Value("${vegan.sendmail}")
    private String fromMail;

    public void sendMail(MailDTO mailDTO, FileDTO file) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(mailDTO.getEmail());
            helper.setFrom(fromMail, "vegan-day");
            helper.setSubject(mailDTO.getTitle());
            helper.setText(mailDTO.getContent(), true);

            FileSystemResource fileImg = new FileSystemResource(new File(filePath + file.getFilepath()));
            helper.addAttachment(file.getFilename(), fileImg);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
