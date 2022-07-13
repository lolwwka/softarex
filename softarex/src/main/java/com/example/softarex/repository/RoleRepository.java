package com.example.softarex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.softarex.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
