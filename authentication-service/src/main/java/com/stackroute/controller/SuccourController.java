package com.stackroute.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuccourController {

//	@PreAuthorize("hasAnyRole('CGI','NDA','DISASTER')")
	@RequestMapping("/succour")
	public String firstPage() {
		return "Welcome to Succour";
	}
}