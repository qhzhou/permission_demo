package com.example.permission_demo.service.impl;

import com.example.permission_demo.service.UserService;
import com.example.permission_demo.service.bo.UserBO;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MockUserService implements UserService {

  private static Map<String, UserBO> users;

  static {
    users = Maps.newHashMap();
    {
      UserBO user = new UserBO();
      user.setId("1");
      user.setName("用户1");
      user.setGroupIds(Lists.newArrayList("1", "2", "3"));
      users.put(user.getId(), user);
    }
    {
      UserBO user = new UserBO();
      user.setId("2");
      user.setName("用户2");
      user.setGroupIds(Lists.newArrayList("2", "3"));
      users.put(user.getId(), user);
    }
    {
      UserBO user = new UserBO();
      user.setId("3");
      user.setName("用户3");
      user.setGroupIds(ImmutableList.of());
      users.put(user.getId(), user);
    }
    {
      UserBO user = new UserBO();
      user.setId("4");
      user.setName("用户4");
      user.setGroupIds(Lists.newArrayList("1", "2", "3"));
      users.put(user.getId(), user);
    }
  }

  @Override
  public List<UserBO> findAll() {
    return ImmutableList.copyOf(users.values());
  }

  @Override
  public UserBO findUserById(String userId) {
    return users.get(userId);
  }
}
