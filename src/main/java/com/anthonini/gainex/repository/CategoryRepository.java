package com.anthonini.gainex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthonini.gainex.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long>  {

}
