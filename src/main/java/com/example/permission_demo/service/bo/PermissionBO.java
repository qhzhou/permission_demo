package com.example.permission_demo.service.bo;

import com.example.permission_demo.core.PermissionObjectType;
import com.example.permission_demo.core.PermissionPrivilege;
import lombok.Data;

@Data
public class PermissionBO extends BaseBO {

  private PermissionObjectType objectType;

  private int objectId;

  private PermissionPrivilege privilege;


}
