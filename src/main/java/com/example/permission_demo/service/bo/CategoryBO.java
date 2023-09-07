package com.example.permission_demo.service.bo;

import com.example.permission_demo.core.CategoryType;
import java.util.List;
import lombok.Data;

@Data
public class CategoryBO extends BaseBO {

  private Integer parentId;
  private CategoryType type;
  private String name;
  private int refId;
  private List<Integer> subCategoryIds;

}
