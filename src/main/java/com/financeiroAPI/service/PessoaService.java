package com.financeiroAPI.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiroAPI.model.Pessoa;
import com.financeiroAPI.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public Pessoa atualizar(Long id, Pessoa pessoa) {
		
		Optional<Pessoa> pessoaSalva = buscaPessoaporID(id);
		
		pessoa.setId(pessoaSalva.get().getId());
		
		return pessoaRepository.save(pessoa);
		
	}


	public void atualizarPropriedadeAtivo(Long id, Pessoa pessoa) {

		Optional<Pessoa> pessoaSalva = buscaPessoaporID(id);
		
		BeanUtils.copyProperties(pessoaSalva, pessoa);
		
		
		pessoaRepository.save(pessoaSalva);
		
	}
	

	private Optional<Pessoa> buscaPessoaporID (Long id) {
		
		Optional<Pessoa> pessoaSalvaID = Optional.of(pessoaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1)));
		
		if(pessoaSalvaID == null) {
			throw new EmptyResultDataAccessException(1);
		}
				
		return pessoaSalvaID;
	}

}
