package ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import _utils.CaptchaUtil;
import _utils.DbUtil;
import _utils.HtmlUtil;
import model.Loginer;

public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 5107652379514786693L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserLoginServlet : back to 'http://localhost:8080/karablog'");
		response.sendRedirect("/karablog");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1- 检查验证码
		boolean captchaValid = CaptchaUtil.isCaptchaValid(request);
		if (!captchaValid) {
			doGet(request, response);
			return;
		}

		// 2- 登入者建模
		Loginer loginer = new Loginer(request);

		// 3- 检查用户名/密码
		Long userid = DbUtil.getLoginerId(loginer);
		if (userid == null || userid.equals("")) {
			doGet(request, response);
			return;
		}

		// 4-跳转主界面
		request.getSession().setAttribute("userid", userid);
		HtmlUtil.printHtml(this, request, response, "main.html");// 打印界面到浏览器
	}
}
