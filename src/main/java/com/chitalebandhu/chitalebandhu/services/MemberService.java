package com.chitalebandhu.chitalebandhu.services;

import com.chitalebandhu.chitalebandhu.entity.Member;
import com.chitalebandhu.chitalebandhu.exceptions.ResourceNotFoundException;
import com.chitalebandhu.chitalebandhu.repository.MemberRepository;
import com.chitalebandhu.chitalebandhu.repository.TaskRepository;
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
    @Autowired
    private TaskRepository taskRepository;

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

    public long getProjectCount(String ownerId, String type){
        return taskRepository.countByOwnerIdAndType(ownerId, type);
    }

    public long getMemberCount(){
        return taskRepository.count();
    }

    public long getStatusCount(String ownerId, String status){
        return taskRepository.countByOwnerIdAndStatus(ownerId, status);
    }

    public Member updateMemberById(String myId, Member newMember){
        Optional <Member> existingMember = memberRepository.findById(myId);

        if(existingMember.isEmpty()){
            return null;
        }

        if(newMember.getEmail() != null && !newMember.getEmail().trim().isEmpty()){
            existingMember.get().setEmail(newMember.getEmail());
        }

        if(newMember.getName() != null && !newMember.getName().trim().isEmpty()){
            existingMember.get().setName(newMember.getName());
        }

        if(newMember.getMobileNo() != null && !newMember.getMobileNo().trim().isEmpty()){
            existingMember.get().setMobileNo(newMember.getMobileNo());
        }

        if(newMember.getRole() != null && !newMember.getRole().trim().isEmpty()){
            existingMember.get().setRole(newMember.getRole());
        }

        return memberRepository.save(existingMember.get());
    }
}
