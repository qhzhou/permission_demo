package com.example.permission_demo.service;

import com.example.permission_demo.core.PermissionObjectType;
import com.example.permission_demo.core.PermissionPrivilege;
import com.example.permission_demo.service.bo.PermissionBO;
import com.example.permission_demo.service.bo.RoleBO;
import com.example.permission_demo.service.bo.UserBO;
import com.google.common.collect.Multimap;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
public class PermissionServiceTest {

  @Autowired
  private PermissionService permissionService;

  @Autowired
  private UserService userService;

  @Test
  public void testFindAllManagementPermission() {
    Multimap<PermissionObjectType, PermissionBO> allManagementPermission = permissionService.findAllManagementPermission();
    Assertions.assertFalse(allManagementPermission.isEmpty());
    for (PermissionObjectType objectType : PermissionObjectType.values()) {
      allManagementPermission.containsKey(objectType);
    }
  }

  @Test
  public void testCreateRoleAndGrantPermission() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        permissionService.createRole(null, null));
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        permissionService.createRole("test", null));
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        permissionService.createRole("新增角色1", Collections.emptyList()));
    Multimap<PermissionObjectType, PermissionBO> allManagementPermission = permissionService.findAllManagementPermission();
    List<Integer> permissionIds = allManagementPermission.get(PermissionObjectType.MODEL).stream()
        .map(PermissionBO::getId).collect(
            Collectors.toList());
    RoleBO role = permissionService.createRole("新增角色2", permissionIds);
    Assertions.assertEquals(role, permissionService.getRole(role.getId()));

    List<UserBO> users = userService.findAll();
    Assertions.assertFalse(users.isEmpty());
    String userId = users.get(0).getId();

    //before assignment
    for (PermissionObjectType objectType : PermissionObjectType.values()) {
      for (PermissionPrivilege privilege : PermissionPrivilege.values()) {
        Assertions.assertFalse(
            permissionService.checkPermissionForUser(objectType, privilege, userId));
      }
    }

    permissionService.assignRoleToUsers(role.getId(), Lists.newArrayList(userId));

    //before assignment
    for (PermissionPrivilege privilege : PermissionPrivilege.values()) {
      Assertions.assertTrue(
          permissionService.checkPermissionForUser(PermissionObjectType.MODEL, privilege, userId));
    }

  }

}
