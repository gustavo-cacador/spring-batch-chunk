package com.gustavoronchi.user_request.processor;

import com.gustavoronchi.user_request.dto.UserDTO;
import com.gustavoronchi.user_request.entities.User;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SelectFieldsUserDataProcessorConfig {

    private static Logger logger = LoggerFactory.getLogger(SelectFieldsUserDataProcessorConfig.class);
    private int counter;

    @Bean
    public ItemProcessor<UserDTO, User> selectFieldsUserDataProcessor() {
        return new ItemProcessor<UserDTO, User>() {
            @Override
            public @Nullable User process(UserDTO item) throws Exception {
                var user = new User();
                user.setLogin(item.getLogin());
                user.setName(item.getName());
                user.setAvatarUrl(item.getAvatarUrl());
                logger.info("[PROCESSOR STEP] select user fields " + counter + " - " + user);
                counter++;
                return user;
            }
        };
    }
}
