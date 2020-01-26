package com.github.joelezell.acme.cert;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.github.joelezell.acme.AcmeApplication;

@RestController
public class CertController {
    // In a production service, GET may also be supported to retrieve information about previously issued certificates.
    
    @PostMapping("/certificates")
    public DeferredResult<ResponseEntity<?>> greeting(@RequestBody CertRequest request) {
        
        // The very first thing that would be done in a production service is authorization. It is critical to verify that the requester is 
        // a legitimate client that is authorized to receive a signed certificate for the given domain. The Acme protocol / framework does a
        // very good job of this. 401 Unauthorized would be sent if there's a violation.
        
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<ResponseEntity<?>>();
        
        // Move to executor thread for this long-running operation to free up the container thread.
        AcmeApplication.getExecutor().execute(() -> {
            ResponseEntity<?> response;
            try {
                response = ResponseEntity.ok(CertGenerator.generateCert(request));
                result.setResult(response);
            } catch (IllegalArgumentException e) {
                // This would probably be logged at a lower level that other exceptions as it is most likely an error on the client side.
                System.out.println("Received Exception" + e);
                e.printStackTrace();
                response = ResponseEntity.badRequest().body(e.getMessage());
                result.setResult(response);
            } catch (Exception e) {
                System.out.println("Received Exception" + e);
                e.printStackTrace();
                response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Error");
                result.setResult(response);
            }
        });

        return result;
    }
}
