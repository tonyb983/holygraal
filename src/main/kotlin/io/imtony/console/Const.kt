@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.imtony.console

import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.api.services.docs.v1.DocsScopes
import com.google.api.services.drive.DriveScopes
import com.google.api.services.sheets.v4.SheetsScopes
import org.kodein.di.*
import org.koin.core.KoinApplication

object Const {
  object ApplicationName {
    const val Value = "HolyGraal"
    const val Name = "applicationName"
  }

  object TokenDirectory {
    const val Value = "S:/FastCode/Kotlin/HolyGraal/tokens/"
    const val Name = "tokenDir"
  }

  object DbPath {
    const val Value = "S:/FastCode/Kotlin/HolyGraal/db/dev/app-db"
    const val Name = "dbPath"
  }

  object DefaultPort {
    const val Value = -1
    const val StringValue = "-1"
    const val Name = "defaultPort"
  }

  object BCrypt {
    object Version {
      val Instance = at.favre.lib.crypto.bcrypt.BCrypt.Version.VERSION_2A
      const val StringValue = "2A"
      const val Name = "bcryptVersion"
    }

    object Cost {
      const val Value = 15
      const val StringValue = "15"
      const val Name = "bcryptCost"
    }
  }

  object GoogleScopes {
    val Instance = ApplicationScopes(
      DriveScopes.DRIVE, DriveScopes.DRIVE_APPDATA, DriveScopes.DRIVE_METADATA,
      DocsScopes.DOCUMENTS,
      SheetsScopes.SPREADSHEETS,
    )
    val Value: Array<String> = arrayOf(
      DriveScopes.DRIVE, DriveScopes.DRIVE_APPDATA, DriveScopes.DRIVE_METADATA,
      DocsScopes.DOCUMENTS,
      SheetsScopes.SPREADSHEETS,
    )
    const val Name = "applicationScopes"
  }

  fun bindToKodein(builder: DI.Builder) {
    builder.constant(tag = ApplicationName.Name) with ApplicationName.Value
    builder.constant(tag = TokenDirectory.Name) with TokenDirectory.Value
    builder.constant(tag = DefaultPort.Name) with DefaultPort.Value
    builder.constant(tag = DbPath.Name) with DbPath.Value
    builder.constant(tag = BCrypt.Cost.Name) with BCrypt.Cost.Value
  }

  fun bindToKoin(koinApp: KoinApplication) {
    koinApp.properties(
      mapOf(
        ApplicationName.Name to ApplicationName.Value,
        TokenDirectory.Name to TokenDirectory.Value,
        DefaultPort.Name to DefaultPort.StringValue,
        DbPath.Name to DbPath.Value,
        BCrypt.Cost.Name to BCrypt.Cost.StringValue,
        BCrypt.Version.Name to BCrypt.Version.StringValue,
      )
    )
  }
}

fun bcryptVersionFrom(input: String): BCrypt.Version = when(input) {
  "2A" -> BCrypt.Version.VERSION_2A
  "2B" -> BCrypt.Version.VERSION_2B
  "2X" -> BCrypt.Version.VERSION_2X
  "2Y" -> BCrypt.Version.VERSION_2Y
  else -> throw IllegalArgumentException("$input is not a valid (or usable) version of BCrypt.")
}

data class ApplicationScopes(val scopes: MutableCollection<String>) {
  constructor(vararg scopes: String) : this(scopes.toMutableList())
}
