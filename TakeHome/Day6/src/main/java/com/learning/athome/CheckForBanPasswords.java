package com.learning.athome;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CheckForBanPasswords extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String passwordFromForm = request.getParameter("pass");
		if (checkForBanPass(passwordFromForm)) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "This password is banned for the usage!!");
		} else
			response.getWriter().write("Given password is applicable!!");

	}

	private boolean checkForBanPass(String passwordFromForm) throws IOException {
		Path myPath = Paths.get("BannedPasswords.txt");
		List<String> lines = Files.readAllLines(myPath, StandardCharsets.UTF_8);
		for (String s : lines) {
			if (s.equals(passwordFromForm))
				return true;
		}
		return false;
	}
}