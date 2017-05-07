package com.shop.dao;

import com.shop.core.model.Area;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangxiao on 2017/5/7.
 */
@Repository
public interface AreaDao {

    @Select("select t.id, t.full_name, t.`name`, t.tree_path, t.parent from xx_area t where grade = 0 ORDER BY orders")
    List<Area> findRootAreas();

    @Select("select t.id, t.full_name, t.`name`, t.tree_path, t.parent from xx_area t where parent=#{parentId} ORDER BY orders")
    List<Area> findChildAreas(@Param(value="parentId")Integer parentId);

    @Select("select t.id, t.full_name, t.`name`, t.tree_path, t.parent from xx_area t where id=#{id}")
    Area findById(Integer area);
}
