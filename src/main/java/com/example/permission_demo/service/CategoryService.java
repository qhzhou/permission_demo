package com.example.permission_demo.service;

import com.example.permission_demo.core.CategoryType;
import com.example.permission_demo.persistence.CategoryRepository;
import com.example.permission_demo.persistence.po.CategoryPO;
import com.example.permission_demo.service.bo.CategoryBO;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;


  public CategoryBO getCategoryTreeById(int id) {
    Optional<CategoryPO> root = categoryRepository.findById(id);
    if (!root.isPresent()) {
      return null;
    }
    List<Integer> subCategoryIds = findSubCategoryIdsByParentId(id);
    return parse(root.get(), subCategoryIds);
  }

  public CategoryBO createEmptyFolder(int parentId, String name) {
    CategoryPO categoryPO = new CategoryPO();
    categoryPO.setName(name);
    categoryPO.setParentId(parentId);
    categoryPO.setType(CategoryType.FOLDER);
    categoryPO.setRefId(0);
    CategoryPO saved = categoryRepository.save(categoryPO);
    return parse(saved, Collections.emptyList());
  }

  public CategoryBO createMetricUnderFolder(int folderId, String name, int metricId) {
    CategoryPO categoryPO = new CategoryPO();
    categoryPO.setName(name);
    categoryPO.setType(CategoryType.METRIC);
    categoryPO.setRefId(metricId);
    categoryPO.setParentId(folderId);
    categoryRepository.save(categoryPO);
    return parse(categoryPO, Collections.emptyList());
  }

  //删除某个指标分类和关联的指标
  //如果该分类有子分类，cascade如果为true，会级联删除，cascade如果为false，会报错
  @Transactional
  public void delete(int categoryId, boolean cascade) {
    log.info("delete id: " + categoryId + " cascade: " + cascade);
    List<Integer> subCategoryIds = findSubCategoryIdsByParentId(categoryId);
    if (subCategoryIds.isEmpty()) {
      categoryRepository.deleteById(categoryId);
    } else {
      Preconditions.checkArgument(cascade,
          String.format("category(id = %d) contains sub category, cascade should be true",
              categoryId));
      categoryRepository.deleteById(categoryId);
      for (Integer subCategoryId : subCategoryIds) {
        delete(subCategoryId, true);
      }
    }
  }

  @VisibleForTesting
  List<Integer> findRefMetricIdsByCategoryId(int id) {
    return categoryRepository.findAllByParentIdAndType(id, CategoryType.METRIC).stream()
        .map(CategoryPO::getRefId).collect(
            Collectors.toList());
  }

  @VisibleForTesting
  List<Integer> findSubCategoryIdsByParentId(int id) {
    return categoryRepository.findAllByParentId(id).stream().map(CategoryPO::getId).collect(
        Collectors.toList());
  }

  private static CategoryBO parse(CategoryPO categoryPO, List<Integer> subCategoryIds) {
    CategoryBO result = new CategoryBO();
    BeanUtils.copyProperties(categoryPO, result);
    result.setSubCategoryIds(subCategoryIds);
    return result;
  }

}
