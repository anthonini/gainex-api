package com.anthonini.gainex.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthonini.gainex.api.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long>  {

}
