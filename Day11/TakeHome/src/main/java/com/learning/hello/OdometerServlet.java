package com.learning.hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.athome.controller.OdometerController;

@WebServlet("/odometer")
public class OdometerServlet extends HttpServlet {

	private static final long serialVersionUID = -7058882339104711568L;
	private OdometerController odc;
	private JakartaServletWebApplication application;
	private TemplateEngine templateEngine;
	// private int size = 5;

	@Override
	public void init() throws ServletException {
		super.init();
		odc = new OdometerController(5);
		odc.reset();
		application = JakartaServletWebApplication.buildApplication(getServletContext());
		final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String click = req.getParameter("click");
//		this.size= Integer.parseInt(req.getParameter("5"));
		if ("nextReading".equals(click)) {
			odc.incrementReading();
			req.setAttribute("nextReading", odc.getReading());
		} else if ("prevReading".equals(click)) {
			odc.decrementReading();
			req.setAttribute("prevReading", odc.getReading());
		}

		var out = resp.getWriter();
		final IWebExchange webExchange = this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);
		ctx.setVariable("Next Reading", odc.nextReading().getReading());
		ctx.setVariable("Previous Reading", odc.prevReading().getReading());
		ctx.setVariable("current", odc.getReading());

		templateEngine.process("odometer", ctx, out);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final IWebExchange webExchange = this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);
		templateEngine.process("odometer", ctx, resp.getWriter());
	}

}