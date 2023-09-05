package com.example.permission_demo.service;

import com.example.permission_demo.service.bo.UserBO;
import java.util.List;

public interface UserService {

  List<UserBO> findAll();

  UserBO findUserById(String userId);


}
