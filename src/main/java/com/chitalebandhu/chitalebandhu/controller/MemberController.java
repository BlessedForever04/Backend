package com.chitalebandhu.chitalebandhu.controller;

import com.chitalebandhu.chitalebandhu.entity.Member;
import com.chitalebandhu.chitalebandhu.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("all")
    public List<Member> getAllMembers(){
        return memberService.getAllMembers();
    }

    @PostMapping
    public boolean addMember(@RequestBody Member member){
        memberService.addMember(member);
        return true;
    }

    @PutMapping("{memberId}/addTask")
    public void addTask(@RequestBody String taskId, @PathVariable String memberId ){
        memberService.addTask(taskId, memberId);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<Member> getMemberById(@PathVariable String myId){
       Member member = memberService.getMemberById(myId);
       return ResponseEntity.ok(member);
    }

    @PutMapping("update/{myId}")
    public boolean updateMemberById(@PathVariable String myId,@RequestBody Member member){
        memberService.updateMemberById(myId , member);
        return true;
    }

    @DeleteMapping("delete/{myId}")
    public boolean deleteMemberById(@PathVariable String myId){
        memberService.deleteMemberById(myId) ;
        return true;
    }
}