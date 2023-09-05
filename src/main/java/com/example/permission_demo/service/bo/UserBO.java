package com.example.permission_demo.service.bo;

import java.util.List;
import lombok.Data;

@Data
public class UserBO {

  private String id;
  private String name;
  private List<String> groupIds;

}
