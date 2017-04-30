package com.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.core.model.FriendLink;
import com.shop.dao.FriendLinkDao;

@Service
public class FriendLinkService {
	
	@Autowired
	private FriendLinkDao friendLinkDao;


	public List<FriendLink> findFriendLinkList(int count) {
		List<FriendLink> friendLinkList = friendLinkDao.findFriendLinkList(count);
		return friendLinkList;
	}
}
