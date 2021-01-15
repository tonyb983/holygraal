package io.imtony.console.auth.users

import at.favre.lib.crypto.bcrypt.BCrypt
import org.kodein.db.model.Id
import org.kodein.db.model.Indexed
import org.kodein.di.DI
import org.kodein.di.conf.DIGlobalAware
import org.kodein.di.instance

data class User(
  @Id val uid: String,
  @Indexed("username_idx") val username: String,
  @Indexed("email_idx") val email: String,
  val password: ByteArray,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is User) return false

    if (uid != other.uid) return false
    if (username != other.username) return false
    if (email != other.email) return false
    if (!password.contentEquals(other.password)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = uid.hashCode()
    result = 31 * result + username.hashCode()
    result = 31 * result + email.hashCode()
    result = 31 * result + password.contentHashCode()
    return result
  }
}
