package com.blogstack.repository;

import com.blogstack.entities.BlogStackBlogCommentMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogStackBlogCommentMasterRepository extends  JpaRepository<BlogStackBlogCommentMaster,Long> {
}
