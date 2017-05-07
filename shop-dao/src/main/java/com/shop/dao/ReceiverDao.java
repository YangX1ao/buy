package com.shop.dao;

import com.shop.core.model.Receiver;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangxiao on 2017/5/6.
 */
@Repository
public interface ReceiverDao {

    @Select("SELECT id,address,area_name,consignee,is_default,phone,zip_code," +
            " area,member from xx_receiver where member=#{loginUserId}")
    public List<Receiver> findAddressById(@Param(value = "loginUserId") Integer loginUserId);

    @Update("UPDATE xx_receiver set is_default=0 WHERE member=#{loginUserId] and is_default=1")
    void updateDefault(@Param(value = "loginUserId") Integer loginUserId);

    void insert(Receiver receiver);

    @Select("select count(id) from xx_receiver where member = #{memberId} and version >= 0")
    Integer count(@Param(value="memberId") Integer loginUserId);

    @Select("select id, address, consignee, phone, zip_code, area, area_name, "
            + "member, is_default from xx_receiver where member = #{memberId} and area = #{area} and consignee = #{consignee} " +
            "and version >= 0 and address = #{address}")
    Receiver findMemeberAreaReceiver(@Param(value="memberId") Integer loginUserId, @Param(value="area") Integer area,
                                     @Param(value="consignee") String consignee, @Param(value="address") String address);


    @Select("select id, address, consignee, phone, zip_code, area, area_name, "
            + "member, is_default from xx_receiver where id = #{id} and version >= 0")
    Receiver findById(@Param(value ="id") Integer receiverId);
}
