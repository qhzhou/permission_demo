package com.example.permission_demo.persistence.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "role_permission_mapping")
@Table(name = "role_permission_mapping")
public class RolePermissionMappingPO extends BasePO {

  @Column(name = "role_id")
  private int roleId;

  @Column(name = "permission_id")
  private int permissionId;

}
