package com.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer> {

}
