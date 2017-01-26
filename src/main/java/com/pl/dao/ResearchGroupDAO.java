package com.pl.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pl.model.ResearchGroup;

@Repository
public interface ResearchGroupDAO extends CrudRepository<ResearchGroup, String>{
	
	@Query(value = ""
			+ "SELECT r "
			+ "FROM grupo r "
			+ "LEFT JOIN FETCH r.members m WHERE "
			+ "r.tic = :name "
			+ "OR "
			+ "(r.englishName = :name) "
			+ "OR "
			+ "(r.spanishName = :name)")
	ResearchGroup searchByString(@Param(value = "name") String name);

}