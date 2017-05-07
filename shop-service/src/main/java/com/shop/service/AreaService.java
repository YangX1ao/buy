package com.shop.service;

import com.shop.core.model.Area;
import com.shop.dao.AreaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangxiao on 2017/5/7.
 */
@Service
public class AreaService {

    @Autowired
    private AreaDao areaDao;

    /***
     * 新增地址时,先获取省市区县
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findAreas(Integer parentId) {
        //通过parentId的存在与否，来进行不同的查询
        //查询第一级的省，直辖市
        List<Area> areas=null;
        if(parentId==null || parentId<1){
            areas=areaDao.findRootAreas();
        }else{
            areas=areaDao.findChildAreas(parentId);
        }
        List<Map<String,Object>> list=new ArrayList<>();
        for(Area area:areas ){
            Map<String,Object> map=new HashMap();
            map.put("name",area.getName());
            map.put("value",area.getId());
            list.add(map);
        }
        return  list;
    }
}
