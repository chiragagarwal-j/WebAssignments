package com.learning.hello;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import controller.NoticeDBController;

@WebServlet("/noticeboard")
public class NoticeBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static {
		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private JakartaServletWebApplication application;
	private TemplateEngine templateEngine;

	NoticeDBController noticeDB = new NoticeDBController();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		application = JakartaServletWebApplication.buildApplication(getServletContext());
		final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final IServletWebExchange webExchange = this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);
		templateEngine.process("noticeboard", ctx, resp.getWriter());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final IServletWebExchange webExchange = this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);

		String buttonId = req.getParameter("btn");
		if (buttonId != null) {
			int index = 0;
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			String name = req.getParameter("name");
			String phone = req.getParameter("phone");
			try {
				noticeDB.saveNotice(title, content, name, phone);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			ResultSet rs = noticeDB.getNotice();
			try {
				while (rs.next() && index <= 6) {
					ctx.setVariable("title" + index, rs.getString("title"));
					ctx.setVariable("content" + index, rs.getString("content"));
					ctx.setVariable("name" + index, rs.getString("name"));
					ctx.setVariable("phone" + index, rs.getString("phone"));
					index += 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			var out = resp.getWriter();

			templateEngine.process("noticeboard", ctx, out);
		}
	}

}