package com.example.permission_demo.service;

import com.example.permission_demo.service.bo.CategoryBO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTest {

  @Autowired
  private CategoryService categoryService;

  @Test
  public void test() {
    Assertions.assertEquals(2, categoryService.findRefMetricIdsByCategoryId(3).size());
  }

  @Test
  public void testSimpleGet() {
    Assertions.assertNull(categoryService.getCategoryTreeById(Integer.MAX_VALUE));
    {
      CategoryBO category = categoryService.getCategoryTreeById(1);
      Assertions.assertEquals(1, category.getId());
      Assertions.assertEquals("爷爷节点", category.getName());
      Assertions.assertEquals(3, category.getSubCategoryIds().size());
    }
    {
      CategoryBO category = categoryService.getCategoryTreeById(2);
      Assertions.assertEquals(2, category.getId());
      Assertions.assertEquals("父亲节点1", category.getName());
      Assertions.assertEquals(0, category.getSubCategoryIds().size());
    }
  }

  @Test
  public void testCreateAndDelete() {
    CategoryBO root = categoryService.getCategoryTreeById(1);
    Integer rootCategoryId = root.getId();
    Assertions.assertEquals(1, rootCategoryId);
    Assertions.assertThrows(IllegalArgumentException.class, () -> categoryService.delete(
        rootCategoryId, false));

    CategoryBO added = categoryService.createEmptyFolder(rootCategoryId, "新增测试");
    Integer addedCategoryId = added.getId();
    System.out.println("added category id: " + addedCategoryId);
    Assertions.assertEquals(root.getSubCategoryIds().size() + 1,
        categoryService.getCategoryTreeById(1).getSubCategoryIds().size());

    for (int i = 0; i < 10; i++) {
      categoryService.createEmptyFolder(addedCategoryId, "新增子分类" + i);
    }

    Assertions.assertEquals(10,
        categoryService.getCategoryTreeById(addedCategoryId).getSubCategoryIds().size());

    categoryService.delete(addedCategoryId, true);
    Assertions.assertEquals(root.getSubCategoryIds().size(),
        categoryService.getCategoryTreeById(1).getSubCategoryIds().size());

  }

}
