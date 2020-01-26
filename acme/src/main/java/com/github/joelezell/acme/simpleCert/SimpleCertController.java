package com.github.joelezell.acme.simpleCert;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.github.joelezell.acme.AcmeApplication;

@RestController
public class SimpleCertController {
    @GetMapping("/cert/{domain}")
    public DeferredResult<ResponseEntity<String>> greeting(@PathVariable String domain) {

        // The very first thing that would be done in a production service is authorization. It is critical to verify that the requester is 
        // a legitimate client that is authorized to receive a signed certificate for the given domain. The Acme protocol / framework does a
        // very good job of this. 401 Unauthorized would be sent if there's a violation.
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<ResponseEntity<String>>();
        
        // Move to executor thread for this long-running operation to free up the container thread.
        AcmeApplication.getExecutor().execute(() -> {
            ResponseEntity<String> response;
            try {
                response = ResponseEntity.ok(SimpleCertGenerator.generateCert(domain));
                result.setResult(response);
            } catch (IllegalArgumentException e) {
                System.out.println("Received Exception" + e);
                e.printStackTrace();
                response = ResponseEntity.badRequest().body("Illegal Argument");
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
