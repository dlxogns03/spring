package com.example.demo.home.members.service;

import org.springframework.stereotype.Service;

import com.example.demo.home.members.dao.MembersDao;

@Service
public class MembersServiceImpl implements MembersService{
	private MembersDao membersDao;
}
