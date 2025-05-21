package com.anthonini.gainex.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthonini.gainex.api.event.CreatedResourceEvent;
import com.anthonini.gainex.api.model.Category;
import com.anthonini.gainex.api.repository.CategoryRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Category> list() {
		return categoryRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category category = categoryRepository.findById(id).orElse(null);
		return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
	}
	
	@PostMapping	
	public ResponseEntity<Category> create(@Valid @RequestBody Category category, HttpServletResponse response) {
		Category createdCategory = categoryRepository.save(category);
		publisher.publishEvent(new CreatedResourceEvent(this, response, createdCategory.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
	}
}
