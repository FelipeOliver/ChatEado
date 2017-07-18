package br.com.chateado.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.chateado.entities.Usuario;

@Controller
public class NavegacaoController {

	
	@RequestMapping(value="/")
	public ModelAndView home(HttpSession session){
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("content_page", "chat");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			try{
				session.setAttribute("usuario", new ObjectMapper().writeValueAsString(usuario));
				session.setAttribute("usuarioObject", usuario);
			}catch( Exception e){
				e.printStackTrace();
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/logout")
	public String logout(){
		return "logout";
	}
}
