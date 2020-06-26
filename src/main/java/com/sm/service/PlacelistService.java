package com.sm.service;

import javax.swing.tree.ExpandVetoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.PlacelistDAO;
import com.sm.domain.PlacelistVo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlacelistService{

	@Autowired
	PlacelistDAO dao;
	
	public void select(PlacelistVo placelistVo) throws ExpandVetoException{
		dao.select(placelistVo);
	}
	
	
	
}
