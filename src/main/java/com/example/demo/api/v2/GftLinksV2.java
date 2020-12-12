package com.example.demo.api.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.example.demo.api.v2.controller.CidadeControllerV2;

@Component
public class GftLinksV2 {

	public Link linkToCidades(String rel) {
		return linkTo(CidadeControllerV2.class).withRel(rel);
	}

	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}

}