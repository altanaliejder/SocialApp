package com.ejder.socialapp.repository;

import com.ejder.socialapp.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Integer> {
}
