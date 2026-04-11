package com.chitalebandhu.chitalebandhu.repository;

import com.chitalebandhu.chitalebandhu.entity.Tasks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Tasks, String> {
    Optional<List<Tasks>> findByOwnerId(String ownerId);
    List<Tasks> findByParentId(String parentId);

    long countByParentIdAndStatus(String parentId, String Status);
    long countByParentId(String parentId);
    long countByParentIdAndStatusIn(String parentId, List<String> statuses);

    Optional <List<Tasks>> findByTypeOrIsProject(String Type , boolean isProject);

    Optional<List<Tasks>> findByStartDateBetween(LocalDateTime start, LocalDateTime end);
    Optional<List<Tasks>> findByDeadLineAfterAndStatusNot(LocalDateTime currentTime, String status);

    long countByType(String type);

    long countByPriority(String priority);

    Optional <List<Tasks>> findByType(String Type);

    long countByOwnerIdAndType(String ownerId, String type);

    long countByOwnerIdAndStatus(String ownerId, String status);

    // Pagination methods
    Page<Tasks> findByType(String type, Pageable pageable);
    //Page<Tasks> findByRootType(String type, Pageable pageable);
    Page<Tasks> findByOwnerId(String ownerId, Pageable pageable);
//    Page<Tasks> findByParentTaskId(String parentId, Pageable pageable);

    // Find tasks/projects that are overdue (deadline passed and not completed/already overdue)
    List<Tasks> findByDeadLineBeforeAndStatusNotIn(LocalDate date, List<String> excludedStatuses);
}
