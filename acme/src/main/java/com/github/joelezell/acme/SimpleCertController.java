package com.github.joelezell.acme;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class SimpleCertController {
    @GetMapping("/cert/{domain}")
    public DeferredResult<ResponseEntity<String>> greeting(@PathVariable String domain) {

        DeferredResult<ResponseEntity<String>> result = new DeferredResult<ResponseEntity<String>>();
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
