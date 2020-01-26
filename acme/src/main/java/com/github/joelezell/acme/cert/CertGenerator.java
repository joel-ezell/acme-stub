package com.github.joelezell.acme.cert;

import java.util.UUID;

public abstract class CertGenerator {
    private static final int DELAY = 10000;
    
    public static final int VALIDITY_PERIOD_MINUTES = 10;
    public static final int VALIDITY_PERIOD_MILLIS = VALIDITY_PERIOD_MINUTES * 60 * 1000;

    public static Certificate generateCert(CertRequest request) throws InterruptedException {
        
        String domain = request.getDomain();
        if (domain.contentEquals("")) {
            // More input validation would be done here in production.
            throw new IllegalArgumentException("Domain must be specified");
        }
        
        Thread.sleep(DELAY);
        
        Certificate response = new Certificate(UUID.randomUUID().toString(),
                request.getDomain(),
                request.getDomain(),
                System.currentTimeMillis(),
                System.currentTimeMillis() + VALIDITY_PERIOD_MILLIS);
        
        // It would probably make sense in production to keep track of all issued and currently valid certificates in a database, with a scheduled job to remove the expired certificates. Things like the validation method could be tracked there.
        return response;
    }
    
}
