package com.pl.dao;

import org.springframework.data.repository.CrudRepository;

import com.pl.model.GroupMember;

public interface GroupMemberDAO extends CrudRepository<GroupMember, Integer> {

}