package com.plantcare.usermanagement.repository;

import com.plantcare.usermanagement.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    
    Optional<UserSession> findBySessionToken(String sessionToken);
    
    Optional<UserSession> findByRefreshToken(String refreshToken);
    
    Optional<UserSession> findByRefreshTokenAndActiveTrue(String refreshToken);
    
    @Modifying
    @Query("UPDATE UserSession s SET s.active = false WHERE s.user.id = :userId AND s.active = true")
    void deactivateAllUserSessions(@Param("userId") Long userId);
    
    @Modifying
    @Query("DELETE FROM UserSession s WHERE s.expiresAt < :now")
    void deleteExpiredSessions(@Param("now") LocalDateTime now);
    
    @Query("SELECT COUNT(s) FROM UserSession s WHERE s.user.id = :userId AND s.active = true")
    long countActiveSessionsByUserId(@Param("userId") Long userId);
}


