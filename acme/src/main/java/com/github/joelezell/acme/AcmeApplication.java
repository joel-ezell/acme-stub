package com.github.joelezell.acme;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class AcmeApplication {
    
    // In a production application, this would be configured through an environment variable.
    private static final int THREAD_POOL_SIZE = 10;    
    // It may make sense in a production application to use the default Spring ThreadPoolTaskExecutor and the @async notation.
    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    // In a production application, this would be configured through an environment variable.
    private static final String MY_FQDN = "acme.joel-ezell.github.com";
    
    private static String myCurrentCert;
    
	public static void main(String[] args) {
		SpringApplication.run(AcmeApplication.class, args);
	}
	
	public static ThreadPoolExecutor getExecutor() {
	    return executor;
	}
	
	// It would probably be good to use the @Async annotation in production for this long-running operation.
    @Scheduled(fixedRate = SimpleCertGenerator.VALIDITY_PERIOD_MILLIS - (60*1000))
    public static void refreshCert()
    {
        try {
            myCurrentCert = SimpleCertGenerator.generateCert(MY_FQDN);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Refreshed cert: " + myCurrentCert);
    }
    
}
