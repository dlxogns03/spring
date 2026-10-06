package com.example.demo.home.files.service;

import org.springframework.stereotype.Service;

import com.example.demo.home.files.dao.FilesDao;

@Service
public class FilesServiceImpl implements FilesService{
	private FilesDao filesDao;
}
