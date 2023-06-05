package notesApp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService implements EmailService{
    private final JavaMailSender javaMailSender;
    @Override
    public void send(String to, String info) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("abeebahmad24@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject("From Notes application");
        mailMessage.setText(info);
        javaMailSender.send(mailMessage);
    }
}