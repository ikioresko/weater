package com.weater.service;

import com.weater.model.Message;
import com.weater.model.User;
import com.weater.model.dto.MessageDto;
import com.weater.repos.MessageRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Page<MessageDto> messageList(Pageable pageable, String filter, User user) {
        if (filter != null && !filter.isEmpty()) {
            return messageRepo.findByTag(filter, pageable, user);
        } else {
            return messageRepo.findAll(pageable, user);
        }
    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User currentUser, User author) {
        return messageRepo.findByUser(pageable, author, currentUser);
    }

    public Page<MessageDto> findAll(Pageable pageable, User currentUser) {
        return messageRepo.findAll(pageable, currentUser);
    }

    public void save(Message message) {
        messageRepo.save(message);
    }
}
