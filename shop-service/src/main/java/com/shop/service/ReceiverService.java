package com.shop.service;

import com.shop.core.model.Area;
import com.shop.core.model.Receiver;
import com.shop.core.util.AssertUtil;
import com.shop.dao.AreaDao;
import com.shop.dao.ReceiverDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiao on 2017/5/6.
 */
@Service
public class ReceiverService {

    @Autowired
    private ReceiverDao receiverDao;

    @Autowired
    private AreaDao areaDao;

    /***
     * 查询用户的收货地址
     * @param loginUserId
     * @return
     */
    public List<Receiver> findAddressById(Integer loginUserId) {
        AssertUtil.isTrue(loginUserId==null ||loginUserId<1,"请登录后在进行操作！");
        List<Receiver> receivers=receiverDao.findAddressById(loginUserId);
        if (receivers != null && receivers.size() > 0) {
            for (int i =0; i < receivers.size(); i++) {
                Receiver receiver = receivers.get(i);
                if (receiver.getIsDefault()) {
                    receivers.remove(receiver);
                    receivers.add(0, receiver);
                    break;
                }
            }
        }
        return receivers;
    }

    /***
     * 添加收货地址
     * @param receiver
     * @param loginUserId
     * @return
     */
    public Receiver addAddress(Receiver receiver, Integer loginUserId) {
        // 基本参数验证
        AssertUtil.isTrue(StringUtils.isBlank(receiver.getConsignee()), "请输入收货人");
        AssertUtil.isTrue(receiver.getArea() == null || receiver.getArea() < 1, "请选择收货地区");
        AssertUtil.isTrue(StringUtils.isBlank(receiver.getAddress()), "请输入收货地址");
        AssertUtil.isTrue(StringUtils.isBlank(receiver.getZipCode()), "请输入邮政编码");
        AssertUtil.isTrue(StringUtils.isBlank(receiver.getPhone()), "请输入收货人手机号");

        //判断是否为默认收货地址
        if(receiver.getIsDefault()){
            //如果是，把其它默认取消
            receiverDao.updateDefault(loginUserId);
        }
        //对用户的收货地址个数进行限制
        Integer count = receiverDao.count(loginUserId);
        AssertUtil.isTrue(count != null && count >= Receiver.MAX_RECEIVER_COUNT,
                "您最多只能添加" + Receiver.MAX_RECEIVER_COUNT + "个收货地址");

        // 查询地区是否添加过
        Receiver receiverFromDb = receiverDao.findMemeberAreaReceiver(loginUserId, receiver.getArea(),
                receiver.getConsignee(), receiver.getAddress());
        AssertUtil.isTrue(receiverFromDb != null, "这个地址已经存在，请重现选择");

        //添加地址
        Area area=areaDao.findById(receiver.getArea());
        AssertUtil.isTrue(area==null,"请选择地区！");
        String areaName=area.getFullName();
        receiver.setAreaName(areaName);
        receiver.setMember(loginUserId);
        receiverDao.insert(receiver);

        return receiver;
    }
}
