package io.imtony.console.auth.users

class Password(input: String) {
 val hashed = PasswordServiceSingleton.hashPassword(input)

}

