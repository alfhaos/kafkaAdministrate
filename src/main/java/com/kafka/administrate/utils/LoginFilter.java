package com.kafka.administrate.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.kafka.administrate.model.member.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String uri = request.getRequestURI();
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userSeesionData");
        if(!uri.equals("/page/member/signIn") && !uri.equals("/page/member/signUp") 
        		&& !uri.equals("/member/signIn") && !uri.equals("/member/signUp") && !uri.equals("/member/checkId")) {
            if (user == null) {
                response.sendRedirect("/page/member/signIn");
                return;
            }
        }


        filterChain.doFilter(request, response);
    
	}

}
