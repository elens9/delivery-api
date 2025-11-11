package com.deliverytech.delivery.repository;


import com.deliverytech.delivery.entity.Notification;
import com.deliverytech.delivery.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, NotificationStatus status);

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Notification> findByPedidoIdOrderByCreatedAtDesc(Long pedidoId);

    Long countByUserIdAndStatus(Long userId, NotificationStatus status);

    @Modifying
    @Query("UPDATE Notification n SET n.status = :status, n.readAt = :readAt WHERE n.id = :id")
    void markAsRead(@Param("id") Long id, @Param("status") NotificationStatus status, @Param("readAt") LocalDateTime readAt);

    @Modifying
    @Query("UPDATE Notification n SET n.status = :status WHERE n.userId = :userId AND n.status = :currentStatus")
    void markAllAsReadByUser(@Param("userId") Long userId,
                             @Param("status") NotificationStatus status,
                             @Param("currentStatus") NotificationStatus currentStatus);
}
