package com.example.permission_demo.service.bo;

import java.util.List;
import lombok.Data;

@Data
public class CategoryBO extends BaseBO {

  private String name;
  private List<Integer> metricIds;
  private List<Integer> subCategoryIds;

}
