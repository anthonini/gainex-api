package com.anthonini.gainex.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthonini.gainex.api.model.Category;
import com.anthonini.gainex.api.repository.CategoryRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public List<Category> list() {
		return categoryRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> findByCode(@PathVariable Long id) {
		Category category = categoryRepository.findById(id).orElse(null);
		return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
	}
	
	@PostMapping	
	public ResponseEntity<Category> create(@RequestBody Category category, HttpServletResponse response) {
		Category createdCategory = categoryRepository.save(category);	
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
	}
}
