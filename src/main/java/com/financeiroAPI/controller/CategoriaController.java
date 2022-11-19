package com.financeiroAPI.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.financeiroAPI.event.RecursoEvent;
import com.financeiroAPI.model.Categoria;
import com.financeiroAPI.model.Pessoa;
import com.financeiroAPI.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Categoria> listartodos(){
		
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	// @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) { //CRIA UMA NOVA CATEGORIA COM TODOS OS CAMPOS
		
		Categoria categoriaSalva = categoriaRepository.save(categoria); //GRAVA NO BANCO
		
		publisher.publishEvent(new RecursoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva); 
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Categoria>> buscaCategoriaId(@PathVariable Long codigo) {
		Optional<Categoria> categoria = categoriaRepository.findById(codigo);
		return categoria != null? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeCategoria(@PathVariable Long codigo) {
		
		categoriaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> atualizaCategoria(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria){
		
		Optional<Categoria> categoriaSalva = categoriaRepository.findById(codigo); 
		categoria.setCodigo(categoriaSalva.get().getCodigo());
		categoriaRepository.save(categoria);
		
		return ResponseEntity.status(HttpStatus.OK).body(categoria);
	}
	

}
