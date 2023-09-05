package com.example.permission_demo.persistence;

import com.example.permission_demo.persistence.po.RolePermissionMappingPO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionMappingRepository extends JpaRepository<RolePermissionMappingPO, Integer> {

  List<RolePermissionMappingPO> findAllByRoleId(int roleId);
}
