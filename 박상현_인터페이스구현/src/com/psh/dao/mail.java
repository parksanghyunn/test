package com.psh.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class mail
 */
@WebServlet("/mail")
public class mail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public mail() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		System.out.println(email);
		// mail server 설정
		// 메일을 어느 계정으로 보낼지 설정해준다.
		//mail server 설정
		String host = "smtp.naver.com";
		String user = "jsap50"; //자신의 네이버 계정
		String password = "@jsapsj5060@";//자신의 네이버 패스워드

		//메일 받을 주소
		String to_email = email;

		 //SMTP 서버 정보를 설정한다.
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        
        //인증 번호 생성기
        StringBuffer temp =new StringBuffer();
        Random rnd = new Random();
        for(int i=0;i<10;i++)
        {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
            case 0:
                // a-z
                temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                break;
            case 1:
                // A-Z
                temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                break;
            case 2:
                // 0-9
                temp.append((rnd.nextInt(10)));
                break;
            }
        }
        String AuthenticationKey = temp.toString();
        System.out.println(AuthenticationKey);
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,password);
            }
        });
        //email 전송
        try {
        	System.out.println("들어옴");
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user, "KH Books"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
            System.out.println("들어옴1");
            
            //메일 제목
            msg.setSubject("안녕하세요 KH BOOKS 인증 메일입니다.");
            //메일 내용
            msg.setText("인증 번호는 :"+temp);
            
            Transport.send(msg);
            System.out.println("이메일 전송");
            
        }catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
        HttpSession saveKey = request.getSession();
        saveKey.setAttribute("AuthenticationKey", AuthenticationKey);
}
}
