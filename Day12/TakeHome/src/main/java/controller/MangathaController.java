package controller;

import java.io.IOException;
import java.io.Writer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import model.Mangatha;

public class MangathaController {
	private Mangatha model;

	public MangathaController() {
		this.model = Mangatha.get();
		model.init();
	}

	public void processGet(IWebExchange webExchange, TemplateEngine templateEngine, HttpServletResponse res)
			throws IOException {
		final WebContext ctx = new WebContext(webExchange);
		Writer out = res.getWriter();
//		String action = webExchange.getRequest().getParameterValue("action");
		if (model.getWinner() != null) {
			System.out.println(model.getWinner());
		}
		ctx.setVariable("isGameStart", model.getIsGameStart());
		ctx.setVariable("inPile", model.getInPile());
		ctx.setVariable("outPile", model.getOutPile());
		ctx.setVariable("players", model.getPlayers());
		templateEngine.process("register", ctx, out);
	}

	public void processPost(IWebExchange webExchange, TemplateEngine templateEngine, HttpServletResponse res)
			throws IOException, ServletException {
		String action = webExchange.getRequest().getParameterValue("action");
		String name = webExchange.getRequest().getParameterValue("name");
		String bet = webExchange.getRequest().getParameterValue("bet");
		String rank = webExchange.getRequest().getParameterValue("rank");
		String suit = webExchange.getRequest().getParameterValue("suit");
		String pos = webExchange.getRequest().getParameterValue("position");

		if (action == null) {
			model.reset();
		} else if (action.equals("add")) {
			int intBet = bet != null && !bet.isEmpty() ? Integer.parseInt(bet) : 5;
			model.addPlayer(name, intBet, pos, rank.concat(suit));
		} else if (action.equals("play")) {
			// TODO
			model.setIsGameStart();
		} else if (action.equals("draw")) {
			model.performAction();
		} else if (action.equals("reset")) {
			model.reset();
		}

		res.sendRedirect(webExchange.getRequest().getRequestPath());
	}
}
