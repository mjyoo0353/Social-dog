package com.jackpot.service;

import java.util.List;

import com.jackpot.domain.DogVO;

public interface DogService {
	// 등록
	public void register(DogVO dog);

	// 조회
	public DogVO get(Long dogId);

	// 수정
	public void modify(DogVO dog);

	// 삭제
	public boolean remove(Long dogId);

	public List<DogVO> getList();
}