package com.plantcare.notificationengine.mapper;

import com.plantcare.notificationengine.dto.NotificationDto;
import com.plantcare.notificationengine.entity.Notification;
import com.plantcare.usermanagement.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-07T18:33:18+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
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
        notificationDto.createdAt( notification.getCreatedAt() );
        notificationDto.id( notification.getId() );
        notificationDto.message( notification.getMessage() );
        notificationDto.read( notification.isRead() );
        notificationDto.title( notification.getTitle() );
        notificationDto.type( notification.getType() );

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
