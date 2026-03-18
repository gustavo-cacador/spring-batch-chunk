package com.gustavoronchi.user_request.domain;

import com.gustavoronchi.user_request.dto.UserDTO;

import java.util.List;

public class ResponseUser {

    private List<UserDTO> content;

    public List<UserDTO> getContent() {
        return content;
    }
}
