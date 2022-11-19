package com.financeiroAPI.controller;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiroAPI.event.RecursoEvent;
import com.financeiroAPI.model.Pessoa;
import com.financeiroAPI.repository.PessoaRepository;
import com.financeiroAPI.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaService pessoaService;
	
	
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> listaPessoas(){
		
		List<Pessoa> pessoas = pessoaRepository.findAll();
		
		return pessoas != null ? new ResponseEntity<List<Pessoa>>(pessoas, HttpStatus.OK) : new ResponseEntity<List<Pessoa>>(HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> listaPessoaPorID(@PathVariable Long id){
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : (ResponseEntity<Pessoa>) ResponseEntity.notFound();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> salvaPessoa(@Valid @RequestBody Pessoa obj, HttpServletResponse response){
		
		Pessoa pessoaSalva = pessoaRepository.save(obj);
		
		publisher.publishEvent(new RecursoEvent(this, response, pessoaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPessoa(@PathVariable Long id) {
		
		pessoaRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizaPessoa(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
		
		Pessoa pessoaSalva = pessoaService.atualizar(id, pessoa);
				
		return ResponseEntity.status(HttpStatus.OK).body(pessoa);
		
	}
	
	@PatchMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Pessoa atualiarPropriedadeAtivo(@PathVariable Long id, @RequestBody Pessoa pessoa) {

		pessoaService.atualizarPropriedadeAtivo(id, pessoa);
		
		return null;
		
	}
	
}
