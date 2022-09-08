package com.snowwarrior.notelog.mapper;

import com.snowwarrior.notelog.model.Note;
import com.snowwarrior.notelog.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteMapper {
    @Select("select * from note where id=#{id}")
    Optional<Note> getNoteById(Long id);

    @Select("select * from note where user_id=#{userId}")
    Optional<Note> getNoteByUserId(Long userId);

    @Insert("insert into note(" +
            "content,delete_at,latest_tag" +
            ")" +
            "values(" +
            "#{content},#{deleteAt},#{latestTag}" +
            ")")
    void addNote(Note note);

    @Update("update note set content=#{content},delete_at=#{deleteAt},latest_at=#{latestAt}" +
            "where id=#{id}")
    void updateNoteById(Note note);

    @Update("update note set content=#{content},delete_at=#{deleteAt},latest_at=#{latestAt}" +
            "where user_id=#{userId}")
    void updateNoteByUserId(Note note);

    @Delete("delete from user where id=#{id}")
    void deleteNoteById(Long id);

    @Delete("delete from user where user_id=#{userId}")
    void deleteNoteByUserId(Long userId);
}
