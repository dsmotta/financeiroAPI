package com.financeiroAPI.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.financeiroAPI.event.RecursoEvent;

public class RecursoListener implements ApplicationListener<RecursoEvent>{

	@Override
	public void onApplicationEvent(RecursoEvent event) {
		
		HttpServletResponse response = event.getResponse();
		Long id = event.getId();
		
		adicionarHeaderLocation(response, id);
		
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(id).toUri();
		response.setHeader("Location",uri.toString());
	}

}
