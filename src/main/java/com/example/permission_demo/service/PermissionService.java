package com.example.permission_demo.service;

import com.example.permission_demo.core.PermissionObjectType;
import com.example.permission_demo.core.PermissionPrivilege;
import com.example.permission_demo.core.PermissionSubjectType;
import com.example.permission_demo.persistence.PermissionRepository;
import com.example.permission_demo.persistence.RolePermissionMappingRepository;
import com.example.permission_demo.persistence.RoleRepository;
import com.example.permission_demo.persistence.SubjectRoleMappingRepository;
import com.example.permission_demo.persistence.po.PermissionPO;
import com.example.permission_demo.persistence.po.RolePO;
import com.example.permission_demo.persistence.po.RolePermissionMappingPO;
import com.example.permission_demo.persistence.po.SubjectRoleMappingPO;
import com.example.permission_demo.service.bo.PermissionBO;
import com.example.permission_demo.service.bo.RoleBO;
import com.example.permission_demo.service.bo.UserBO;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
import com.google.common.collect.Multimap;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService {

  @Autowired
  private UserService userService;

  @Autowired
  private PermissionRepository permissionRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private SubjectRoleMappingRepository subjectRoleMappingRepository;

  @Autowired
  private RolePermissionMappingRepository rolePermissionMappingRepository;


  public Multimap<PermissionObjectType, PermissionBO> findAllManagementPermission() {
    Builder<PermissionObjectType, PermissionBO> builder = ImmutableListMultimap.builder();
    permissionRepository.findAllByObjectId(0).stream().map(PermissionService::convert)
        .forEach(permissionBO -> builder.put(permissionBO.getObjectType(), permissionBO));
    return builder.build();
  }

  private static PermissionBO convert(PermissionPO permissionPO) {
    PermissionBO result = new PermissionBO();
    BeanUtils.copyProperties(permissionPO, result);
    return result;
  }

  @Transactional
  public RoleBO createRole(String name, List<Integer> permissionIds) {
    Preconditions.checkArgument(StringUtils.isNotEmpty(name), "name cannot be empty");
    Preconditions.checkArgument(permissionIds != null && !permissionIds.isEmpty(),
        "permission ids cannot be empty");
    RolePO role = new RolePO();
    role.setName(name);
    roleRepository.save(role);
    int roleId = role.getId();
    rolePermissionMappingRepository.saveAll(permissionIds.stream().map(id -> {
      RolePermissionMappingPO rolePermissionMappingPO = new RolePermissionMappingPO();
      rolePermissionMappingPO.setRoleId(roleId);
      rolePermissionMappingPO.setPermissionId(id);
      return rolePermissionMappingPO;
    }).collect(Collectors.toList()));
    return convert(role, permissionIds);
  }

  public RoleBO getRole(int roleId) {
    Optional<RolePO> role = roleRepository.findById(roleId);
    if (!role.isPresent()) {
      return null;
    }
    List<Integer> permissionIds = rolePermissionMappingRepository.findAllByRoleId(roleId).stream()
        .map(RolePermissionMappingPO::getPermissionId).collect(
            Collectors.toList());
    return convert(role.get(), permissionIds);
  }

  public void assignRoleToUsers(int roleId, List<String> userIds) {
    subjectRoleMappingRepository.saveAll(
        userIds.stream().map(userId -> {
          SubjectRoleMappingPO subjectRoleMappingPO = new SubjectRoleMappingPO();
          subjectRoleMappingPO.setSubjectType(PermissionSubjectType.USER);
          subjectRoleMappingPO.setSubjectId(userId);
          subjectRoleMappingPO.setRoleId(roleId);
          return subjectRoleMappingPO;
        }).collect(
            Collectors.toList()));
  }

  private static RoleBO convert(RolePO rolePO, List<Integer> permissionIds) {
    RoleBO result = new RoleBO();
    BeanUtils.copyProperties(rolePO, result);
    result.setPermissionIds(permissionIds);
    return result;
  }

  //FIXME: performance is not good, must optimize
  private Set<Integer> getPermissionIdsForUser(String userId) {
    UserBO user = userService.findUserById(userId);
    Preconditions.checkNotNull(user, "user with id: " + userId + " does not exist");
    List<String> groupIds = user.getGroupIds();

    Stream<Integer> groupRoleIds = groupIds.stream().flatMap(
        groupId -> subjectRoleMappingRepository.findAllBySubjectTypeAndSubjectId(
            PermissionSubjectType.USER_GROUP,
            groupId).stream()).map(SubjectRoleMappingPO::getRoleId);
    Stream<Integer> userRoleIds = subjectRoleMappingRepository.findAllBySubjectTypeAndSubjectId(
        PermissionSubjectType.USER, userId).stream().map(SubjectRoleMappingPO::getRoleId);

    return Streams.concat(groupRoleIds, userRoleIds).distinct()
        .flatMap(roleId -> rolePermissionMappingRepository.findAllByRoleId(roleId).stream())
        .map(RolePermissionMappingPO::getPermissionId).collect(
            Collectors.toSet());
  }

  public boolean checkPermissionForUser(PermissionObjectType objectType,
      PermissionPrivilege privilege, String userId) {
    Set<Integer> permissionIds = getPermissionIdsForUser(userId);
    return permissionRepository.findAllById(permissionIds).stream().anyMatch(
        permission -> permission.getObjectType() == objectType
            && permission.getPrivilege() == privilege);
  }


}
