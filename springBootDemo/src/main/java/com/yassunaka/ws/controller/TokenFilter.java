package com.yassunaka.ws.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class TokenFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String header = req.getHeader("Authorization");
		
		if (header == null || !header.startsWith("Bearer "))
			throw new ServletException("Token inexistente ou inválido");
			
		String token = header.substring(7);
		try {
			Jwts.parser().setSigningKey("banana").parseClaimsJws(token).getBody();			
		} catch (Exception e) {
			//throw new ServletException("Token inválido");
			((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
		}
		
		//chain.doFilter(request, response);
	}
}
