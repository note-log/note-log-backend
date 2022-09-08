package com.snowwarrior.notelog.mapper;

import com.snowwarrior.notelog.model.Note;
import com.snowwarrior.notelog.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteMapper {
    @Results(id = "noteResultMap", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "deleteAt", column = "delete_at"),
            @Result(property = "latestTag", column = "latest_tag")
    })
    @Select("select * from note where id=#{id}")
    Optional<Note> getNoteById(Long id);

    @ResultMap("noteResultMap")
    @Select("select * from note where user_id=#{userId} and delete_at is null")
    List<Note> getNoteByUserId(Long userId);

    @Insert("insert into note(" +
            "content, delete_at, latest_tag, user_id" +
            ")" +
            "values(" +
            "#{content}, #{deleteAt}, #{latestTag}, #{userId}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addNote(Note note);

    @Update("update note set content=#{content},delete_at=#{deleteAt},latest_tag=#{latestTag} " +
            "where id=#{id}")
    void updateNoteById(Note note);

    @Delete("update note set delete_at = now() where id = #{id}")
    void deleteNoteById(Long id);

    @Delete("delete from note where user_id=#{userId}")
    void deleteNoteByUserId(Long userId);
}
