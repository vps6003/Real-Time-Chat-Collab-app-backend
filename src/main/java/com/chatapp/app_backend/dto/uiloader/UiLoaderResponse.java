package com.chatapp.app_backend.dto.uiloader;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UiLoaderResponse {

    private String id;
    private String username;
    private String email;
}
