package com.github.joelezell.acme.simpleCert;

public abstract class SimpleCertGenerator {
    private static final int DELAY = 10000;
    
    public static final int VALIDITY_PERIOD_MINUTES = 10;
    public static final int VALIDITY_PERIOD_MILLIS = VALIDITY_PERIOD_MINUTES * 60 * 1000;

    public static String generateCert(String domain) throws InterruptedException {
        
        Thread.sleep(DELAY);
        
        if (domain == "") {
            // More input validation would be done here in production.
            throw new IllegalArgumentException("Null domain received");
        }
        
        // Attach the expiration time to the end of the returned string. A true X.509 certificate has the "Not Before" and "Not After"
        // timestamps included in the signed document. It would probably make sense in production to keep track of all issued and currently valid certificates
        // in a database, with a scheduled job to remove the expired certificates. Things like the validation method could be tracked there.
        return "foo-" + domain + "-expires-" + (System.currentTimeMillis() + VALIDITY_PERIOD_MILLIS);
    }
    
}
