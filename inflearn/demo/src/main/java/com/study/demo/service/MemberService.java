package com.study.demo.service;

import com.study.demo.domain.Member;
import com.study.demo.repository.MemberRepository;
import com.study.demo.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
//@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService (MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원 가입
     */


    public Long join(Member member) {
        long start = System.currentTimeMillis();

        try {
            validateDuplicateMember(member); // 같은 이름이 있는 중복 회원 X
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }
    }

    private void validateDuplicateMember(Member member) {
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        // Object.isPresent : 객체에 값이 있으면 true, 없으면 false 반환
//        // Object.ifPresent : 객체에 값이 있으면 실행 없으면 넘어감
//
//        // result 가 존재 => 같은 name을 가진 Member 객체가 이미 존재한다.
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });

    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
