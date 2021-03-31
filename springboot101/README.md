#### Assignment

## User Registration

1. Update `UserService`
   1. Add `getUser(email)` the finds and return the `User` with the specified `email`
2. Add field `boolean: verified` with default value `false` in class `User`
3. Creat a class `VerificationService`
   1. Move `Map<email, code>` data structure here
   2. Create `addVerificatioCode(email, code)` that saves `key: email` and `value: code`
   3. Create `verifyUser(email, code)` to check if the pair `email` and `code` exists. If it is, and then delete the verification code entry and in `UserService`, use `getUser(email)` and update its `verified` to `true`
4. Inject `VerificationService` to UserService
   1. Replace verification code implementation to use `VerificationService`
   ```java
        String verificationCode = idService.generateCode(5);

        verificationCodes.put(newUser.getEmail(), verificationCode);
        users.add(newUser);
   ```
5. Improve email format validation in `UserService`
6. Hash `User.password` before saving
