package com.example.permission_demo.persistence;

import com.example.permission_demo.persistence.po.CategoryPO;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryRepositoryTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  public void test() {
    Optional<CategoryPO> optional = categoryRepository.findById(1);
    Assertions.assertTrue(optional.isPresent());
    Assertions.assertNotNull(optional.map(CategoryPO::getCreateTime));
    Assertions.assertNotNull(optional.map(CategoryPO::getUpdateTime));
    Assertions.assertEquals(4, categoryRepository.findAllById(
        Lists.newArrayList(1, 2, 3, 4, 5, 6)).size());
    Assertions.assertEquals(3, categoryRepository.findAllByParentId(1).size());

    CategoryPO category = optional.get();

    Assertions.assertTrue(categoryRepository.updateNameById(category.getId(), "renamed") > 0);

    CategoryPO renamed = categoryRepository.findById(category.getId())
        .orElseThrow(RuntimeException::new);
    Assertions.assertEquals(renamed.getCreateTime().getTime(), category.getCreateTime().getTime());
    Assertions.assertTrue(renamed.getUpdateTime().after(category.getUpdateTime()));
    Assertions.assertTrue(categoryRepository.updateNameById(category.getId(), category.getName()) > 0);


  }

}
