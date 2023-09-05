package com.example.permission_demo.persistence.po;

import com.example.permission_demo.core.PermissionSubjectType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;


@Data
@Entity(name = "subject_role_mapping")
@Table(name = "subject_role_mapping")
public class SubjectRoleMappingPO extends BasePO {

  @Column(name = "role_id")
  private int roleId;

  @Column(name = "subject_type")
  @Enumerated(EnumType.STRING)
  private PermissionSubjectType subjectType;

  @Column(name = "subject_id")
  private String subjectId;

}
