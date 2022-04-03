package com.generation.LojaDeGames.Controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.generation.lojagames.model.Produto;
import br.org.generation.lojagames.repository.CategoriaRepository;
import br.org.generation.lojagames.repository.ProdutoRepository;

@RestController	
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	ResponseEntity<List<Produto>>getAll(){
		return ResponseEntity.ok().body(repository.findAll());}
	@GetMapping ("/id")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		if(repository.existsById(id)) {
			return ResponseEntity.ok(repository.getById(id));
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}	
	@PostMapping
	public ResponseEntity<Produto>post(@Valid @RequestBody Produto produto){
		if(categoriaRepository.existsById(produto.getCategoria().getId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	@PutMapping
	public ResponseEntity<Produto>put(@Valid @RequestBody Produto produto){
		if(categoriaRepository.existsById(produto.getCategoria().getId())) {
			return ResponseEntity.ok().body(repository.save(produto));
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}
	}
	@DeleteMapping ("/id")
	public ResponseEntity<Produto>delete(@PathVariable Long id){
		if(repository.existsById(id)) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}
	}

}