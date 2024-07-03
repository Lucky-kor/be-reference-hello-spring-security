package com.springboot.member;

public interface MemberService {
    Member createMember(Member member);
    Member findMember(String email);
}
