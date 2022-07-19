package com.example.softarex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.softarex.entity.Field;

public interface FieldRepository extends JpaRepository<Field, Long> {

}
