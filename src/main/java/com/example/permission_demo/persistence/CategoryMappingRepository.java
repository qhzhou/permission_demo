package com.example.permission_demo.persistence;

import com.example.permission_demo.persistence.po.CategoryMappingPO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMappingRepository extends JpaRepository<CategoryMappingPO, Integer> {

  List<CategoryMappingPO> findAllByCategoryIdAndType(int categoryId, int type);

  int deleteByCategoryId(int categoryId);

}
