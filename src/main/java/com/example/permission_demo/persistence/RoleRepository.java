package com.example.permission_demo.persistence;

import com.example.permission_demo.persistence.po.RolePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RolePO, Integer> {

}
