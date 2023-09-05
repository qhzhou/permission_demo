package com.example.permission_demo.service.bo;

import java.util.Date;
import lombok.Data;

@Data
public abstract class BaseBO {

  private Integer id;
  private Date createTime;
  private Date updateTime;

}
