package io.imtony.console.di.koin

import at.favre.lib.crypto.bcrypt.BCrypt
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import io.imtony.console.Const
import io.imtony.console.Resources
import io.imtony.console.auth.users.PasswordService
import io.imtony.console.auth.users.PasswordServiceInjected
import io.imtony.console.auth.users.PasswordServiceWithDi
import io.imtony.console.google.services.*
import org.kodein.db.DB
import org.kodein.db.impl.open
import org.koin.dsl.module

val consoleAppModule = module {
  single { Const.GoogleScopes.Instance }
  single<JsonFactory> { JacksonFactory.getDefaultInstance() }
  single<HttpTransport> { GoogleNetHttpTransport.newTrustedTransport() }
  single {
    createCredentials(
      get(),
      get(),
      get(),
      getProperty(Resources.GCreds.Credentials.Name),
      getProperty(Const.TokenDirectory.Name),
      getProperty(Const.DefaultPort.Name).toInt(),
    )
  }
  single<ServiceInitializer> {
    createInjectedServiceInitializer(get(), get(), get(), getProperty(Const.ApplicationName.Name))
  }
  single<GoogleDocsService> { GoogleDocsInjectedService(get()) }
  single<GoogleDriveService> { GoogleDriveInjectedService(get()) }
  single<GoogleSheetsService> { GoogleSheetsInjectedService(get()) }
  single<GoogleCalendarService> { GoogleCalendarInjectedService(get()) }

  factory { Const.BCrypt.Version.Instance }
  factory { LongPasswordStrategies.hashSha512(get()) }
  factory { BCrypt.with(get(), get()) }
  factory { BCrypt.verifyer(get(), get())}
  factory<PasswordService> { PasswordServiceInjected(getProperty(Const.BCrypt.Cost.StringValue).toInt(), get(), get()) }

  single { DB.open(getProperty(Const.DbPath.Name)) }
}
