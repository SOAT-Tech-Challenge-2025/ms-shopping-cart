package com.store.msshoppingcart.order.infrastructure.adapters.out.client;

import com.store.msshoppingcart.order.infrastructure.adapters.in.dto.UserAuthResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userAuthClient", url = "${api.url}")
public interface UserAuthClient {

    @GetMapping(value = "/me", produces = "application/json")
    UserAuthResponseDTO getUserInfo(@RequestHeader("Authorization") String bearerToken);
}