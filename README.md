# outsouce-me-api-microservices

## What have to be done?

### Development

- Decouple user-profile and job-offer service from static-data by duplicating Technology entity across microservices. Static-data service will provide other
  services with technology updates extending publish-subscribe pattern
- <s> Provide Request-Id (unique request identifier) downstream across services - should be generated automatically on api gateway level and passsed downstream in
  the header </s>
- Integrate Zipkin platform
- Create missing endpoints for admin panel platform
- Adjust indices based on slow queries
- Add cache to opinions service
- <s>Configure shedlock dependency for leader election (CRON operations)</s>

### Configuration

- Set up k8s clusters
- Prepare fixtures for presentation