package br.com.chateado.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavegacaoController {

	@RequestMapping(value="/")
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("content_page", "chat");
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
