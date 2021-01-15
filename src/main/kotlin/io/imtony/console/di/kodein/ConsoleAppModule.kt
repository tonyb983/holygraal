package io.imtony.console.di.kodein

import at.favre.lib.crypto.bcrypt.BCrypt
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies
import at.favre.lib.crypto.bcrypt.LongPasswordStrategy
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import io.imtony.console.ApplicationScopes
import io.imtony.console.Const
import io.imtony.console.Resources
import io.imtony.console.auth.users.PasswordService
import io.imtony.console.auth.users.PasswordServiceWithDi
import io.imtony.console.google.services.*
import org.kodein.db.DB
import org.kodein.db.impl.open
import org.kodein.di.*

enum class GoogleServiceType {
  Drive, Docs, Sheets, Calendar;
}

val consoleAppModule = DI.Module("ConsoleApp") {
  Const.bindToKodein(this)
  Resources.bindToKodein(this)

  bind<ApplicationScopes>() with instance(Const.GoogleScopes.Instance)
  bind<JsonFactory>() with instance(JacksonFactory.getDefaultInstance())
  bind<HttpTransport>() with instance(GoogleNetHttpTransport.newTrustedTransport())
  bind<Credential>() with singleton {
    createCredentials(
      instance(),
      instance(),
      instance(),
      instance(Resources.GCreds.Credentials.Name),
      instance(Const.TokenDirectory.Name),
      instance(Const.DefaultPort.Name),
    )
  }

  bind<ServiceInitializer>() with provider { createDiServiceInitializer(di) }
//  bind<AbstractGoogleJsonClient>().subTypes() with { type ->
//    when(type.jvmType) {
//      Drive::class.java -> singleton { instance<ServiceInitializer>().createDrive() }
//      Sheets::class.java -> singleton { instance<ServiceInitializer>().createSheets() }
//      Docs::class.java -> singleton { instance<ServiceInitializer>().createDocs() }
//      Calendar::class.java -> singleton { instance<ServiceInitializer>().createCalendar() }
//      else -> throw NotImplementedError("Unable to create service for requested type ${type.jvmType}. JvmType: $type")
//    }
//  }
//
//  bind<GoogleService<*>>().subTypes() with { type ->  when(type.getGenericParameters().first()) {
//    Drive::class.java -> singleton { GoogleDriveInjectedService(instance()) }
//    Sheets::class.java -> singleton { GoogleSheetsInjectedService(instance()) }
//    Docs::class.java -> singleton { GoogleDocsInjectedService(instance()) }
//    Calendar::class.java -> singleton { GoogleCalendarInjectedService(instance()) }
//    else -> throw NotImplementedError("Unable to create service for requested type ${type.jvmType}. JvmType: $type")
//  } }

  bind<GoogleDocsService>() with singleton { GoogleDocsInjectedService(instance()) }
  bind<GoogleDriveService>() with singleton { GoogleDriveInjectedService(instance()) }
  bind<GoogleSheetsService>() with singleton { GoogleSheetsInjectedService(instance()) }
  bind<GoogleCalendarService>() with singleton { GoogleCalendarInjectedService(instance()) }

  bind<BCrypt.Version>() with instance(Const.BCrypt.Version.Instance)
  bind<LongPasswordStrategy>() with provider { LongPasswordStrategies.hashSha512(instance()) }
  bind<BCrypt.Hasher>() with provider { BCrypt.with(instance(), instance())}
  bind<BCrypt.Verifyer>() with provider { BCrypt.verifyer(instance(), instance()) }
  bind<PasswordService>() with provider { PasswordServiceWithDi(di) }

  bind<DB>() with singleton { DB.open(instance(Const.DbPath.Name)) }


}

private fun tester() {

}
