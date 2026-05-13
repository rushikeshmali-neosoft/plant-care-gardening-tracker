package com.plantcare.notificationengine.mapper;

import com.plantcare.notificationengine.dto.NotificationDto;
import com.plantcare.notificationengine.entity.Notification;
import com.plantcare.usermanagement.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-12T13:34:57+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Oracle Corporation)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationDto toDto(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationDto.NotificationDtoBuilder notificationDto = NotificationDto.builder();

        notificationDto.userId( notificationUserId( notification ) );
        notificationDto.id( notification.getId() );
        notificationDto.title( notification.getTitle() );
        notificationDto.message( notification.getMessage() );
        notificationDto.type( notification.getType() );
        notificationDto.read( notification.isRead() );
        notificationDto.createdAt( notification.getCreatedAt() );

        return notificationDto.build();
    }

    private Long notificationUserId(Notification notification) {
        if ( notification == null ) {
            return null;
        }
        User user = notification.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
