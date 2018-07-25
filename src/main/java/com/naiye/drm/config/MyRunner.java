package com.naiye.drm.config;

import com.naiye.drm.util.DisableSSLCertificateCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Override
    public void run(String... strings) throws Exception {
        logger.info("取消对证书的验证");
        DisableSSLCertificateCheckUtil.disableChecks();
    }

}


