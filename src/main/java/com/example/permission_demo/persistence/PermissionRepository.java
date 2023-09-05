package com.example.permission_demo.persistence;

import com.example.permission_demo.persistence.po.PermissionPO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionPO, Integer> {

  List<PermissionPO> findAllByObjectId(int objectId);

}
