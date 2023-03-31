package com.example.demo.repository;

import com.example.demo.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.*;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>,
        PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);

    @Query(value = "SELECT * FROM users u WHERE u.EMAIL_VERIFICATION_STATUS = true",
            countQuery = "SELECT COUNT(*) FROM users u WHERE u.EMAIL_VERIFICATION_STATUS = true",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);
}
