package com.activitytracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.activitytracker.entity.UserActivity;

public interface ActivityRepository extends JpaRepository<UserActivity, Integer> {

}
