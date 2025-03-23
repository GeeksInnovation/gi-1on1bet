package com._on1bet.authservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class _on1betResponse {

    private boolean serviceStatus;
    private String serviceMsg;
    private ApiResponse data;

}
