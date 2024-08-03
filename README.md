# outsouce-me-api-microservices

## What have to be done?

### Development

- <s> Decouple user-profile and job-offer service from static-data by duplicating Technology entity across microservices. Static-data service will provide other
  services with technology updates extending publish-subscribe pattern</s>
- <s> Provide Request-Id (unique request identifier) downstream across services - should be generated automatically on api gateway level and passsed downstream in
  the header </s>
- <s>Configure shedlock dependency for leader election (CRON operations)</s>
- Create missing endpoints for admin panel platform
- Adjust indices based on slow queries
- Add cache to opinions service
- user profile cache is not clearing after updating thumbnails
- opinions details are not sent to user-service at fixtures start up

### Configuration

- <s>Set up k8s clusters</s>
- Prepare fixtures for presentation