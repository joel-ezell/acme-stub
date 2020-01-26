# acme-stub

This project provides a very simple stub implementation for automated certificate issuance. The /cert endpoint behaves 
pretty much as requested. The only addition that was made is that the expiration time for the issued "certificate" is appended to the string.

An additional /certificate endpoint has been added. This has the following differences from the /cert endpoint:

* Certificates are generated using the POST method instead of the GET method. Most REST guidelines state that GET invocations should be idempotent, and should not cause resources to be generated.

* The domain is specified in the body of the request rather than the URL. Generally, we would only want an identifier for the resource to be present in the URL if the client was in charge of the resulting resource URL.

* More detailed certificate information is provided in a JSON message body in the response.
