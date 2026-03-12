package com.chitalebandhu.chitalebandhu.repository;

import com.chitalebandhu.chitalebandhu.entity.Tasks;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Tasks, String> {
    Optional<List<Tasks>> findByOwnerId(String ownerId);

    long countByParentTaskIdAndStatus(String parentTaskId, String Status);

    long countByType(String type);

    Optional <List<Tasks>> findByType(String Type);

    long countByOwnerIdAndType(String ownerId, String type);

    long countByOwnerIdAndStatus(String ownerId, String status);
}
