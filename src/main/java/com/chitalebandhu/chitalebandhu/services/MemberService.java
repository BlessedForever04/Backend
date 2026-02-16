package com.chitalebandhu.chitalebandhu.services;

import com.chitalebandhu.chitalebandhu.entity.Member;
import com.chitalebandhu.chitalebandhu.exceptions.ResourceNotFoundException;
import com.chitalebandhu.chitalebandhu.repository.MemberRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void addMember(Member member){
        memberRepository.save(member);
    }
    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }
    public Member getMemberById(String myId){
        return memberRepository.findById(myId).orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + myId));
    }
    public void deleteMemberById(String myId){
        memberRepository.deleteById(myId);
    }
    public void updateMemberById(String myId, Member member){
        Member existingMember = memberRepository.findById(myId).orElseThrow( () -> new ResourceNotFoundException("Member not found with id: "+ myId));
        existingMember.setName(member.getName());
        System.out.println("Incoming tasks: " + member.getTasks());
        System.out.println("Existing tasks: " + existingMember.getTasks());
        if(member.getTasks() != null){
            existingMember.getTasks().addAll(member.getTasks());
        }
        memberRepository.save(existingMember);
    }

    public void addTask(String taskId, String memberId) {
        Member tempMember = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member doesn't exist"));
        tempMember.addTask(taskId);
        memberRepository.save(tempMember);
    }
}