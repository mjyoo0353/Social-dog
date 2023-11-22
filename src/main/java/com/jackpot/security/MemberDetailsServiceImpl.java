package com.jackpot.security;

import com.jackpot.domain.MemberVO;
import com.jackpot.mapper.AdminMapper;
import com.jackpot.mapper.MemberMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j
public class MemberDetailsServiceImpl implements UserDetailsService {

    @Autowired
    MemberMapper memberMapper;
    
    @Autowired
    AdminMapper adminMapper;
    
    @Override
    public UserDetails loadUserByUsername(String memberLoginId) throws UsernameNotFoundException {
        log.warn("Load Member by Member Login ID: " + memberLoginId);

        // 1. admin 인지 검사
        MemberVO vo = memberMapper.get(memberLoginId);
        if(vo == null){
        	// 2 member인지 검사 
        	
        	
            throw new UsernameNotFoundException(memberLoginId + "은/는 없는 ID입니다.");
        }

        log.warn("member vo : " + vo);
        return vo;
        

    }
}