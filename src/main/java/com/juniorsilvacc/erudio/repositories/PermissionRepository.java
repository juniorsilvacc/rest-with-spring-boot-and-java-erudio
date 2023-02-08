package com.juniorsilvacc.erudio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorsilvacc.erudio.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{
}
