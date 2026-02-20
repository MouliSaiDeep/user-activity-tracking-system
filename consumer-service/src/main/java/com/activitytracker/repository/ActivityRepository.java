package com.activitytracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.activitytracker.entity.UserActivity;

import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<UserActivity, Integer> {

}
