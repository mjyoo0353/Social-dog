package com.jackpot.service;

import java.util.List;

import com.jackpot.domain.ParticipantVO;
import com.jackpot.mapper.ParticipantMapper;

public class ParticipantServiceImpl implements ParticipantService {

	ParticipantMapper participantMapper;
	
	@Override
	public void create(ParticipantVO participant) {
		participantMapper.insertSelectKey(participant);
		Long participantId = participant.getParticipantId();

	}

	@Override
	public ParticipantVO get(Long participantId) {
		ParticipantVO participant = participantMapper.get(participantId);
		return participant;
	}

	@Override
	public boolean delete(Long participantId) {
		return participantMapper.delete(participantId);
	}

	@Override
	public List<ParticipantVO> getList() {
		return participantMapper.getList();
	}

}
