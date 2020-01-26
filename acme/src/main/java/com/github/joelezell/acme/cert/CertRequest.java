package com.github.joelezell.acme.cert;

import com.fasterxml.jackson.annotation.JsonCreator;

/*
 * This class represents the JSON data that will be returned to the client. This is a rough approximation of some of the data that 
 * would be included in a true X.509 certificate, which is encoded using ASN.1 and is digitally signed.
 */
public class CertRequest {
    private final String domain;
    
    @JsonCreator
    public CertRequest(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

}
