package com.ujobs.WebServices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujobs.WebServices.model.Follower;
import com.ujobs.WebServices.model.User;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    List<Follower> findByFollower(User follower);

    List<Follower> findByFollowed(User followed);

    boolean existsByFollowerAndFollowed(User follower, User followed);

    void deleteByFollowerAndFollowed(User follower, User followed);
}
