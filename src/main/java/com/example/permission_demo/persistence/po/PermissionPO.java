package com.example.permission_demo.persistence.po;

import com.example.permission_demo.core.PermissionObjectType;
import com.example.permission_demo.core.PermissionPrivilege;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "permission")
@Table(name = "permission")
public class PermissionPO extends BasePO {

  @Column(name = "object_type")
  @Enumerated(EnumType.STRING)
  private PermissionObjectType objectType;

  @Column(name = "object_id")
  private int objectId;

  @Column(name = "privilege")
  @Enumerated(EnumType.STRING)
  private PermissionPrivilege privilege;

}
