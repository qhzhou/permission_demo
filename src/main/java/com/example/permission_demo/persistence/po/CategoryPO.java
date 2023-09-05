package com.example.permission_demo.persistence.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "category")
@Table(name = "category")
@ToString(callSuper = true)
public class CategoryPO extends BasePO {

  private String name;

  @Column(name = "parent_id")
  private Integer parentId;

}
