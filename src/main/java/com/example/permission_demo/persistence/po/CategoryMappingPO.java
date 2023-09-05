package com.example.permission_demo.persistence.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "category_mapping")
@Table(name = "category_mapping")
@EqualsAndHashCode(callSuper = true)
public class CategoryMappingPO extends BasePO {

  @Column(name = "category_id")
  private int categoryId;

  private int type;

  @Column(name = "ref_id")
  private int refId;

}
