package com.example.permission_demo.persistence;

import com.example.permission_demo.persistence.po.CategoryPO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryPO, Integer> {

  List<CategoryPO> findAllByParentId(Integer parentId);

  @Modifying
  @Query(value = "update category set name = :name, update_time = now() where id = :id")
  @Transactional
  int updateNameById(@Param("id") int id, @Param("name") String name);



}
