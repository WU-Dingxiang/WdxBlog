package top.kara.blog.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HtmlUtil {
	public static void printHtml(HttpServlet servlet,
			HttpServletRequest request, HttpServletResponse response,
			String htmlName) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		String path = servlet.getServletContext().getRealPath(
				"/html/" + htmlName);
		System.out.println("HtmlUtil.path = " + path);

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println("HtmlUtil : invalid html name");
			response.sendRedirect("/blog-web");
		}

		if (fis != null) {
			byte[] buff = new byte[10 * 1024];
			int len = 0;
			OutputStream os = response.getOutputStream();

			while ((len = fis.read(buff)) > 0) {
				os.write(buff, 0, len);
			}

			os.close();
			fis.close();
		}
	}
}
