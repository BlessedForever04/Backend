package com.chitalebandhu.chitalebandhu.repository;

import com.chitalebandhu.chitalebandhu.entity.Tasks;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Tasks, String> {
    Optional<List<Tasks>> findByOwnerId(String ownerId);
    List<Tasks> findByParentTaskId(String parentTaskId);

    long countByParentTaskIdAndStatus(String parentTaskId, String Status);
    long countByParentTaskId(String parentTaskId);
    long countByParentTaskIdAndStatusIn(String parentTaskId, List<String> statuses);

    long countByType(String type);

    long countByPriority(String priority);

    Optional <List<Tasks>> findByType(String Type);

    long countByOwnerIdAndType(String ownerId, String type);

    long countByOwnerIdAndStatus(String ownerId, String status);
}
