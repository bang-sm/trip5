package com.sm.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.domain.WeatherLocalVO;

@Repository
public class WeatherLocalDAO {

	@Autowired
	SqlSession sqlSession;


	// 첫번째 칸
	public List<WeatherLocalVO> weatherLocalName(WeatherLocalVO vo) {
		return sqlSession.selectList("mappers.weatherlocalMapper.weatherLocalName");
	}


	public List<WeatherLocalVO> selectAllWeather() {
		
		
		return sqlSession.selectList("mappers.weatherlocalMapper.lowLocalWeather");
	}

	/*
	 * // 두번째 칸 public List<WeatherLocalVO> localCateSec(int parent) { return
	 * sqlSession.selectList(namespace); }
	 */

	
	
}
