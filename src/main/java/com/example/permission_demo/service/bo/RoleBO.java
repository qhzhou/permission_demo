package com.example.permission_demo.service.bo;

import java.util.List;
import lombok.Data;

@Data
public class RoleBO extends BaseBO {

  private String name;
  private List<Integer> permissionIds;

}
