package com.learning.hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FibonacciServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static int fibonacci(int n) {
		if (n <= 1)
			return n;
	return fibonacci(n - 1) + fibonacci(n - 2);
	}

	private static List<Integer> fibonaccis(int n) {
		return IntStream.range(0, n + 1).mapToObj(x -> fibonacci(x)).collect(Collectors.toList());
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int limit = Integer.valueOf(request.getParameter("n"));
		out.println(fibonaccis(limit));
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int limit = Integer.valueOf(request.getParameter("formLimit"));
		out.println(fibonaccis(limit));
	}
}