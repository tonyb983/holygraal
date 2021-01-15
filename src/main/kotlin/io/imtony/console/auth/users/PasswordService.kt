package io.imtony.console.auth.users

import at.favre.lib.crypto.bcrypt.BCrypt
import io.imtony.console.Const
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.conf.DIGlobalAware
import org.kodein.di.constant
import org.kodein.di.instance

interface PasswordService {
  fun hashPassword(input: String): ByteArray
  fun hashPasswordToString(input: String): String
  fun hashPassword(input: CharArray): ByteArray
  fun hashPasswordToString(input: CharArray): String

  fun checkPassword(input: String, hashedPass: String): Boolean = checkPasswordToResult(input, hashedPass).verified
  fun checkPasswordToResult(input: String, hashedPass: String): BCrypt.Result
  fun checkPassword(input: String, hashedPass: ByteArray): Boolean = checkPasswordToResult(input, hashedPass).verified
  fun checkPasswordToResult(input: String, hashedPass: ByteArray): BCrypt.Result
  fun checkPassword(input: ByteArray, hashedPass: ByteArray): Boolean = checkPasswordToResult(input, hashedPass).verified
  fun checkPasswordToResult(input: ByteArray, hashedPass: ByteArray): BCrypt.Result
}

class PasswordServiceInjected(
  private val bcryptCost: Int,
  private val hasher: BCrypt.Hasher,
  private val verifier: BCrypt.Verifyer,
) : PasswordService {
  override fun hashPassword(input: String): ByteArray = hasher.hash(bcryptCost, input.toCharArray())
  override fun hashPasswordToString(input: String): String = hasher.hashToString(bcryptCost, input.toCharArray())
  override fun hashPassword(input: CharArray): ByteArray = hasher.hash(bcryptCost, input)
  override fun hashPasswordToString(input: CharArray): String = hasher.hashToString(bcryptCost, input)

  override fun checkPassword(input: String, hashedPass: String): Boolean = verifier.verify(input.toCharArray(), hashedPass).verified
  override fun checkPasswordToResult(input: String, hashedPass: String): BCrypt.Result = verifier.verify(input.toCharArray(), hashedPass)
  override fun checkPassword(input: String, hashedPass: ByteArray): Boolean = verifier.verify(input.toCharArray(), hashedPass).verified
  override fun checkPasswordToResult(input: String, hashedPass: ByteArray): BCrypt.Result = verifier.verify(input.toCharArray(), hashedPass)
  override fun checkPassword(input: ByteArray, hashedPass: ByteArray): Boolean = verifier.verify(input, hashedPass).verified
  override fun checkPasswordToResult(input: ByteArray, hashedPass: ByteArray): BCrypt.Result = verifier.verify(input, hashedPass)
}

class PasswordServiceWithDi (override val di: DI) : PasswordService, DIAware {
  private val bcryptCost: Int by constant()
  private val hasher by instance<BCrypt.Hasher>()
  private val verifier by instance<BCrypt.Verifyer>()

  override fun hashPassword(input: String): ByteArray = hasher.hash(bcryptCost, input.toCharArray())
  override fun hashPasswordToString(input: String): String = hasher.hashToString(bcryptCost, input.toCharArray())
  override fun hashPassword(input: CharArray): ByteArray = hasher.hash(bcryptCost, input)
  override fun hashPasswordToString(input: CharArray): String = hasher.hashToString(bcryptCost, input)

  override fun checkPassword(input: String, hashedPass: String): Boolean = verifier.verify(input.toCharArray(), hashedPass).verified
  override fun checkPasswordToResult(input: String, hashedPass: String): BCrypt.Result = verifier.verify(input.toCharArray(), hashedPass)
  override fun checkPassword(input: String, hashedPass: ByteArray): Boolean = verifier.verify(input.toCharArray(), hashedPass).verified
  override fun checkPasswordToResult(input: String, hashedPass: ByteArray): BCrypt.Result = verifier.verify(input.toCharArray(), hashedPass)
  override fun checkPassword(input: ByteArray, hashedPass: ByteArray): Boolean = verifier.verify(input, hashedPass).verified
  override fun checkPasswordToResult(input: ByteArray, hashedPass: ByteArray): BCrypt.Result = verifier.verify(input, hashedPass)
}

object PasswordServiceSingleton : DIGlobalAware, PasswordService {
  private val bcryptCost: Int by constant()
  private val hasher by instance<BCrypt.Hasher>()
  private val verifier by instance<BCrypt.Verifyer>()

  override fun hashPassword(input: String): ByteArray = hasher.hash(bcryptCost, input.toCharArray())
  override fun hashPasswordToString(input: String): String = hasher.hashToString(bcryptCost, input.toCharArray())
  override fun hashPassword(input: CharArray): ByteArray = hasher.hash(bcryptCost, input)
  override fun hashPasswordToString(input: CharArray): String = hasher.hashToString(bcryptCost, input)

  override fun checkPassword(input: String, hashedPass: String): Boolean = verifier.verify(input.toCharArray(), hashedPass).verified
  override fun checkPasswordToResult(input: String, hashedPass: String): BCrypt.Result = verifier.verify(input.toCharArray(), hashedPass)
  override fun checkPassword(input: String, hashedPass: ByteArray): Boolean = verifier.verify(input.toCharArray(), hashedPass).verified
  override fun checkPasswordToResult(input: String, hashedPass: ByteArray): BCrypt.Result = verifier.verify(input.toCharArray(), hashedPass)
  override fun checkPassword(input: ByteArray, hashedPass: ByteArray): Boolean = verifier.verify(input, hashedPass).verified
  override fun checkPasswordToResult(input: ByteArray, hashedPass: ByteArray): BCrypt.Result = verifier.verify(input, hashedPass)
}
