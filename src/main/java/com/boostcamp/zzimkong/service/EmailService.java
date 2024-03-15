package com.boostcamp.zzimkong.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendMail(String email, String fileName, String statusMessage, String statusCode) {
        try {
            // TODO: status code도 받아서 error면 fail message, finish면 성공 메시지
            MimeMessage message = null;
            if (statusCode.equals("ERROR")) {
                message = createFailMessage(email, fileName, statusMessage);
            } else {
                message = createSuccessMessage(email, fileName);
            }
            javaMailSender.send(message);

            log.info("Success");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.info("fail");
        }
    }

    private MimeMessage createSuccessMessage(String to, String fileName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(String.format("업로드한 %s가 3D로 완성되었습니다!", fileName));
        StringBuilder msgg = new StringBuilder();

        msgg.append("<div style='margin:100px;'>");
        msgg.append("<h1> 안녕하세요, 찜콩 서비스를 이용해주셔서 감사합니다.</h1>");
        msgg.append("<br>");
        msgg.append("<p>  업로드하신 데이터를 성공적으로 변환하였습니다.</h1>");
        msgg.append("<br>");
        msgg.append("<p>  사이트에서 변환된 3D 데이터를 확인하실 수 있습니다.<p>");
        msgg.append("<br>");
        msgg.append("<p>\uD83D\uDC49 https://zzimkong.ggm.kr/<p>");
        msgg.append("<br>");
        msgg.append("<p>  추가로 다양한 공간, 가구 데이터를 업로드해보세요!<p>");
        msgg.append("<br>");
        msgg.append("<p> 이번 UXR에 참여해주셔서 감사합니다. 아래 링크로 이동하셔서 설문을 참여하시면 스타벅스 기프티콘을 드릴 예정입니다.<p>");
        msgg.append("<p> \uD83D\uDC49 https://forms.gle/9qbk9Epy27cFN3K66<p>");
        msgg.append("<p> \uD83D\uDE4F\uD83D\uDE4F 많은 참여 부탁드려요. \uD83D\uDE4F\uD83D\uDE4F<p>");
        msgg.append("<br>");
        msgg.append("<p>  추가 문의사항 있으시다면 다음으로 연락주세요.<p>");
        msgg.append("<p> 찜콩 보여줘 홈즈 서비스 문의<p>");
        msgg.append("<p> \uD83D\uDC49 https://open.kakao.com/o/s5emEncg<p>");
        msgg.append("<br>");
        msgg.append("<p> - 찜콩 드림 -<p>");
        msgg.append("</div>");

        message.setText(msgg.toString(), "utf-8", "html");
        message.setFrom(new InternetAddress("bcatcv5@gmail.com", "찜콩"));

        return message;
    }

    private MimeMessage createFailMessage(String to, String fileName, String statusMessage) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(String.format("업로드한 %s의 3D 제작 과정 중 오류가 발생했습니다.", fileName));

        StringBuilder msgg = new StringBuilder();
        msgg.append("<div style='margin:100px;'>");
        msgg.append("<h1> 안녕하세요, 찜콩 서비스를 이용해주셔서 감사합니다.</h1>");
        msgg.append("<br>");
        msgg.append("<p> 아쉽게도, 업로드하신 데이터를 변환하는데에 실패하였습니다.</h1>");
        msgg.append("<br>");
        msgg.append("<p> 사유: " + statusMessage + "<p>");
        msgg.append("<br>");
        msgg.append("<p> 열심히 만들어보려했으나 귀하께서 업로드한 데이터(이미지 혹은 영상)로는 재구성하기가 어렵습니다.<p>");
        msgg.append("<br>");
        msgg.append("<p> [업로드 가이드]를 확인하신 후, 적절한 데이터로 다시 한 번 업로드 해주세요. \uD83D\uDC49 https://zzimkong.ggm.kr/<p>");
        msgg.append("<br>");
        msgg.append("<p> 외의 문제라고 생각될 시에는 다음으로 연락주시면 빠른 시일 내에 답변 드리겠습니다.<p>");
        msgg.append("<br>");
        msgg.append("<p> 불편을 끼쳐드려 죄송합니다.<p>");
        msgg.append("<p> 찜콩 보여줘 홈즈 서비스 문의<p>");
        msgg.append("<p> \uD83D\uDC49 https://open.kakao.com/o/s5emEncg<p>");
        msgg.append("<br>");
        msgg.append("<p> 그럼에도 이번 UXR에 참여해주셔서 감사합니다. 아래 링크로 이동하셔서 설문을 참여하시면 스타벅스 기프티콘을 드릴 예정입니다.<p>");
        msgg.append("<p> \uD83D\uDC49 https://forms.gle/9qbk9Epy27cFN3K66<p>");
        msgg.append("<br>");
        msgg.append("<p> - 찜콩 드림 -<p>");
        msgg.append("</div>");

        message.setText(msgg.toString(), "utf-8", "html");
        message.setFrom(new InternetAddress("bcatcv5@gmail.com", "찜콩"));

        return message;
    }
}
