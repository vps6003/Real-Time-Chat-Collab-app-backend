package com.chatapp.app_backend.service;

import com.chatapp.app_backend.dto.uiloader.UiLoaderResponse;

public interface UiLoaderService {

    UiLoaderResponse load(String email);

}
