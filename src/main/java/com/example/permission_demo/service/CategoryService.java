package com.example.permission_demo.service;

import com.example.permission_demo.persistence.po.CategoryMappingPO;
import com.example.permission_demo.persistence.CategoryMappingRepository;
import com.example.permission_demo.persistence.po.CategoryPO;
import com.example.permission_demo.persistence.CategoryRepository;
import com.example.permission_demo.service.bo.CategoryBO;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  private static final int TYPE_METRICS = 1;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryMappingRepository categoryMappingRepository;


  public CategoryBO getCategoryTreeById(int id) {
    Optional<CategoryPO> root = categoryRepository.findById(id);
    if (!root.isPresent()) {
      return null;
    }
    List<Integer> refMetricIds = findRefMetricIdsByCategoryId(id);
    List<Integer> subCategoryIds = findSubCategoryIdsByParentId(id);
    return parse(root.get(), refMetricIds, subCategoryIds);
  }

  public CategoryBO create(int parentId, String name) {
    CategoryPO categoryPO = new CategoryPO();
    categoryPO.setName(name);
    categoryPO.setParentId(parentId);
    CategoryPO saved = categoryRepository.save(categoryPO);
    return parse(saved, Collections.emptyList(), Collections.emptyList());
  }

  //删除某个指标分类和关联的指标
  //如果该分类有子分类，cascade如果为true，会级联删除，cascade如果为false，会报错
  @Transactional
  public void delete(int categoryId, boolean cascade) {
    System.out.println("delete id: " + categoryId + " cascade: " + cascade);
    List<Integer> subCategoryIds = findSubCategoryIdsByParentId(categoryId);
    if (subCategoryIds.isEmpty()) {
      categoryRepository.deleteById(categoryId);
      categoryMappingRepository.deleteByCategoryId(categoryId);
    } else {
      Preconditions.checkArgument(cascade,
          String.format("category(id = %d) contains sub category, cascade should be true",
              categoryId));
      categoryRepository.deleteById(categoryId);
      categoryMappingRepository.deleteByCategoryId(categoryId);
      for (Integer subCategoryId : subCategoryIds) {
        delete(subCategoryId, true);
      }
    }
  }

  @VisibleForTesting
  List<Integer> findRefMetricIdsByCategoryId(int id) {
    return categoryMappingRepository.findAllByCategoryIdAndType(id, TYPE_METRICS).stream().map(
        CategoryMappingPO::getRefId).collect(Collectors.toList());
  }

  @VisibleForTesting
  List<Integer> findSubCategoryIdsByParentId(int id) {
    return categoryRepository.findAllByParentId(id).stream().map(CategoryPO::getId).collect(
        Collectors.toList());
  }

  private static CategoryBO parse(CategoryPO categoryPO, List<Integer> metricIds,
      List<Integer> subCategoryIds) {
    CategoryBO result = new CategoryBO();
    BeanUtils.copyProperties(categoryPO, result);
    result.setMetricIds(metricIds);
    result.setSubCategoryIds(subCategoryIds);
    return result;
  }

}
