[//]: # (TODO: add disctiption)

### Add client to the DB

1. Go to https://bcrypt-generator.com/
2. Generate secret with Rounds == 10. And remember original secret!
3. Execute SQL script
    ``` sql
    INSERT INTO oauth_client_details
      (
        client_id,
        client_secret,
        scope,
        authorized_grant_types,
        access_token_validity,
        refresh_token_validity,
        autoapprove
      )
    VALUES
      (
        "<client_id>",
        "<encrypted_client_secret>",
        "basic",
        "password,refresh_token",
        3600,
        86400,
        true
      );
    ```
4. Go to https://www.debugbear.com/basic-auth-header-generator
5. Generate base64 encoded header with a username (<client_id> from step 3) and a password (<client_secret> from step 2)
6. Use the header on the client side to gain access token

### Run tests with coverage

1. ``` gradlew jacocoTestReport```
2. open ***{PROJECT_HOME}**/build/reports/jacoco/test/html/index.html*
