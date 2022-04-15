package com.sarfaraz.management.controller;

import com.sarfaraz.management.exception.FileStorageException;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

//@Controller
//@RequestMapping(value = { "", "/" })
public class PublicCtrl {

//	@RequestMapping(value = { "/", "", "index", "home" })
//	private Object index(HttpSession session) {
//		JSONObject obj = new JSONObject().put("status", true);
//		// return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
//		return obj;
//	}

//	@GetMapping("/login")
//	private ResponseEntity<String> login() {
//		return ResponseEntity.ok().body("Not Done");
//	}

//	@RequestMapping(value = { "/register" })
//	private String register() {
//		return "user/register";
//	}

//	@RequestMapping(value = { "/forgot" })
//	private String forgot() {
//		return "login/forgot-password";
//	}
//
//	@RequestMapping(value = { "/recover-password" })
//	private String recoverPassword() {
//		return "login/recover-password";
//	}

}
