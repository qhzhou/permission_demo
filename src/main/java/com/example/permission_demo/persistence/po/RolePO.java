package com.example.permission_demo.persistence.po;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "role")
@Table(name = "role")
public class RolePO extends BasePO {

  private String name;

}
