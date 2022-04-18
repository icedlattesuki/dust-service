package com.dust.infra;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.TreeMap;

@Component
public class ObjectStorageManager {

    private final String bucket;

    private final String region;

    private final String secretId;

    private final String secretKey;

    private final Integer tmpCredentialAge;

    private final COSClient cosClient;

    @Autowired
    public ObjectStorageManager(Environment env) {
        bucket = env.getProperty("cos.bucket");
        region = env.getProperty("cos.region");
        secretId = env.getProperty("cos.secretId");
        secretKey = env.getProperty("cos.secretKey");
        tmpCredentialAge = env.getProperty("cos.tmpCredentialAge", Integer.class);

        COSCredentials credentials = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        cosClient = new COSClient(credentials, clientConfig);
    }

    public Response generateTmpCredential() throws IOException {
        TreeMap<String, Object> config = new TreeMap<>();
        config.put("bucket", bucket);
        config.put("region", region);
        config.put("secretId", secretId);
        config.put("secretKey", secretKey);
        config.put("durationSeconds", tmpCredentialAge);
        config.put("allowPrefixes", new String[] {"avatar/*"});
        String[] allowActions = new String[] {
                "name/cos:PutObject",
                "name/cos:PostObject",
                "name/cos:GetObject"
        };
        config.put("allowActions", allowActions);

        return CosStsClient.getCredential(config);
    }

    public String getObjectUrl(String key) throws CosClientException {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key, HttpMethodName.GET);
        request.setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000));
        return cosClient.generatePresignedUrl(request).toString();
    }
}
