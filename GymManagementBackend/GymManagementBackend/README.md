# Gym Management Backend

## Kafka + MailHog local runbook

1. Start local Kafka and MailHog from the repository root:

```powershell
docker compose up -d
```

2. Start the backend:

```powershell
cd GymManagementBackend/GymManagementBackend
./mvnw spring-boot:run
```

3. Assign a trainer to a user through the existing admin API/UI:
- `PUT /api/admin/users/{userId}/assign/{trainerId}`

4. Verify notifications:
- MailHog UI: `http://localhost:8025`
- Kafka UI: `http://localhost:8081`

## Notes

- Topic used: `trainer.assignment.v1`
- Dead-letter topic suffix: `.dlt`
- Notification emails are sent asynchronously from Kafka events.
