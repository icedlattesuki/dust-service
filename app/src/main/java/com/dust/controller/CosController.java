package com.dust.controller;

import com.dust.infra.ObjectStorageManager;
import com.tencent.cloud.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CosController {

    @Autowired
    private ObjectStorageManager objectStorageManager;

    @GetMapping("/api/cos/tmp-credential")
    public Response getTmpCredential() throws IOException {
        return objectStorageManager.generateTmpCredential();
    }
}
