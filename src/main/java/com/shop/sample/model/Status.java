package com.shop.sample.model;

import lombok.Getter;

@Getter
public enum Status {
    OK(200),
    Bad_Request(400),
    Not_Found(404),
    Method_Not_Allowed(405),
    Internal_Server_Error(500);

    private String status;
    private int code;

    Status(int code){
        this.code = code;
    }

}
