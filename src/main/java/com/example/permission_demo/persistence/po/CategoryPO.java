package com.example.permission_demo.persistence.po;

import com.example.permission_demo.core.CategoryType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

  @Enumerated(value = EnumType.STRING)
  private CategoryType type;

  @Column(name = "ref_id")
  private int refId;

}
