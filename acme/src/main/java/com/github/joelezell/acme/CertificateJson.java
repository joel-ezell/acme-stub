package com.github.joelezell.acme;

/*
 * This class represents the JSON data that will be returned to the client. This is a rough approximation of some of the data that 
 * would be included in a true X.509 certificate, which is encoded using ASN.1 and is digitally signed.
 */
public class CertificateJson {
    // Not actually included in a true X.509 certificate, but derived from information in the cert.
    private final String fingerprint;
    // Part of the data in the Subject field in an X.509 cert
    private final String CN;
    // The value checked by modern browsers
    private final String SAN;
    
    // Validity period, expressed using UNIX epoch
    private final long validNotBefore;
    private final long validNotAfter;
    
    // If doing production code, I would probably use a builder pattern rather than have a long public constructor signature like this.
    public CertificateJson(String fingerprint, String cn, String san, long validNotBefore, long validNotAfter) {
        this.fingerprint = fingerprint;
        this.CN = cn;
        this.SAN = san;
        this.validNotBefore = validNotBefore;
        this.validNotAfter = validNotAfter;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public String getCN() {
        return CN;
    }

    public String getSAN() {
        return SAN;
    }

    public long getValidNotBefore() {
        return validNotBefore;
    }

    public long getValidNotAfter() {
        return validNotAfter;
    }

}
