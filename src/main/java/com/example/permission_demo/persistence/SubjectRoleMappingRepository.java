package com.example.permission_demo.persistence;

import com.example.permission_demo.core.PermissionSubjectType;
import com.example.permission_demo.persistence.po.SubjectRoleMappingPO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRoleMappingRepository extends JpaRepository<SubjectRoleMappingPO, Integer> {

  List<SubjectRoleMappingPO> findAllBySubjectTypeAndSubjectId(PermissionSubjectType subjectType,
      String subjectId);

}
