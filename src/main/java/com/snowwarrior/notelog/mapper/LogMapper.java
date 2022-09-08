package com.snowwarrior.notelog.mapper;

import com.snowwarrior.notelog.model.Log;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LogMapper {
    @Results(id = "logResultMap", value = {
            @Result(property = "noteId", column = "note_id"),
            @Result(property = "createdAt", column = "created_at")
    })
    @Select("select * from log where id = #{id}")
    Log getLogById(Long id);

    @ResultMap("logResultMap")
    @Select("select * from log where note_id = #{noteId}")
    List<Log> getLogByNoteId(Long noteId);

    @ResultMap("logResultMap")
    @Select("select * from log where note_id = #{noteId} and location = #{location}")
    List<Log> getLogByNoteIdAndLocation(Long noteId, String location);

    @ResultMap("logResultMap")
    @Select("select * from log where note_id = #{noteId} and created_at between #{startTime} and #{endTime}")
    List<Log> getLogByNoteIdAndTime(Long noteId, Date startTime, Date endTime);

    @Insert("insert into log (note_id, content, location, created_at) " +
            "values " +
            "(#{noteId}, #{content}, #{location}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertLog(Log log);

    @Delete("delete from log where id = #{id}")
    void deleteLogById(Long id);
}
