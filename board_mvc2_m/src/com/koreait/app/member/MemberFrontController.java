package com.koreait.app.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;

public class MemberFrontController extends HttpServlet{

	/**
	 * JVM의 메모리에서만 머물러 있건 객체를 시스템이 종료되거나 네트워크로 통신을 할 때에도 그대로 사용할 수 있도록
	 * 영구화 목적으로 직렬화를 사용한다
	 * 직렬화된 객체는 byte형태로 변환이 되어 있으며, 다시 사용하고 싶다면 역직렬화를 통해서 객체로 변환된다
	 * 이때 SUID(serialVersionUID)라는 값을 고정 시켜 구분점으로 사용해서 사용자가 원하는 객체가 맞는지 판단하게 된다
	 * 자주 변경되는 클래스 객체에는 사용하지 않는 것이 좋다.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String command = requestURI.substring(contextPath.length());
		
		ActionForward forword = null;
		System.out.println("requestURI :"+requestURI);
		System.out.println("contextPath : "+contextPath);
		System.out.println("command : "+command);
		switch (command) {
		case "/member/MemberJoin.me":
			forword = new ActionForward();
			forword.setRedirect(false);
			forword.setPath("/app/member/joinForm.jsp");
			break;
		case "/member/MemberJoinOk.me":
			try {
				forword = new MemberJoinOkAction().execute(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/member/MemberCheckId.me":
			try {
				forword = new MemberCheckIdAction().execute(req,resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/member/MemberLogin.me":
			forword = new ActionForward();
			forword.setRedirect(false);
			forword.setPath("/app/member/loginForm.jsp");
			break;
		case "/member/MemberLoginOk.me":
			try {
				forword = new MemberLoginOkAction().execute(req,resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default://비정상적인 경로일 경우
			forword = new ActionForward();
			forword.setRedirect(false);
			forword.setPath("/app/error/404.jsp");
			break;
		}
		
		//forward가 null이 아니라면 응답을 이 곳에서 처리하겠다는 의미
		if(forword != null) {
			if(forword.isRedirect()) {
				//전송방식이 redirect
				resp.sendRedirect(forword.getPath());
			}else {
				//전송방식이 forward
				RequestDispatcher dispatcher = req.getRequestDispatcher(forword.getPath());
				dispatcher.forward(req, resp);
			}
		}
	}
	
}
