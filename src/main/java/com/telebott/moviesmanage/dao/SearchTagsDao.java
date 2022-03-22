package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.SearchTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchTagsDao extends JpaRepository<SearchTags, Integer>, CrudRepository<SearchTags, Integer> {
    @Query(value = "SELECT *, COUNT( *)  AS c FROM `search_tags` GROUP BY `context` ORDER BY c DESC LIMIT 50", nativeQuery = true)
    List<SearchTags> getHots();
    @Query(value = "SELECT *, COUNT( *)  AS c FROM `search_tags` GROUP BY `context` ORDER BY c DESC LIMIT :limit", nativeQuery = true)
    List<SearchTags> getHots(int limit);
    SearchTags findAllById(long id);
}
