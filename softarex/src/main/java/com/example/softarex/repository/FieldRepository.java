package com.example.softarex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.softarex.entity.Field;

public interface FieldRepository extends JpaRepository<Field, Long> {

    @Transactional
    @Modifying
    @Query("update Field f set f.active = ?1, f.label = ?2, f.required = ?3, f.type = ?4, f.options = ?5 where f.id = ?6")
    void updateFieldInfoById(boolean active, String label, boolean required, String type,String options, long id);

    List<Field> findAllByActiveTrue();
}
