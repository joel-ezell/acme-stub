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
    @PostMapping("/certificates")
    public DeferredResult<ResponseEntity<?>> greeting(@RequestBody CertRequest request) {
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<ResponseEntity<?>>();
        
        // Move to executor thread for this long-running operation to free up the container thread.
        AcmeApplication.getExecutor().execute(() -> {
            ResponseEntity<?> response;
            try {
                response = ResponseEntity.ok(CertGenerator.generateCert(request));
                result.setResult(response);
            } catch (IllegalArgumentException e) {
                System.out.println("Received Exception" + e);
                e.printStackTrace();
                response = ResponseEntity.badRequest().body("");
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
