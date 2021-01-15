package io.imtony.console.google.services

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.docs.v1.Docs
import com.google.api.services.drive.Drive
import com.google.api.services.sheets.v4.Sheets
import io.imtony.console.ApplicationScopes
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.constant
import org.kodein.di.instance
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader

fun createJsonFactory(): JacksonFactory = JacksonFactory.getDefaultInstance()
fun createHttpTransport(): NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
/**
 * ### Creates the [Credential] object for this app to access google's service apis.
 *
 * @param[httpTrans] The [HttpTransport] implementation to be used for the [GoogleAuthorizationCodeFlow.Builder].
 * @param[jsonFac] The [JsonFactory] implementation to be used in [GoogleClientSecrets.load] and [GoogleAuthorizationCodeFlow.Builder]
 * @return[Credential] The application credential object.
 */
fun createCredentials(
  httpTrans: HttpTransport,
  jsonFac: JsonFactory,
  applicationScopes: ApplicationScopes,
  credInputFile: String,
  tokenOutputDir: String,
  localPort: Int,
): Credential {
  val inStream = Unit::class.java.getResourceAsStream(credInputFile)
    ?: throw FileNotFoundException("Resource not found: $credInputFile")

  val clientSecrets = GoogleClientSecrets.load(jsonFac, InputStreamReader(inStream))

  val flow = GoogleAuthorizationCodeFlow.Builder(httpTrans, jsonFac, clientSecrets, applicationScopes.scopes)
    .setDataStoreFactory(FileDataStoreFactory(File(tokenOutputDir)))
    .setAccessType("offline")
    .build()

  val receiver = LocalServerReceiver.Builder().setPort(localPort).build()
  return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
}

fun createInjectedServiceInitializer(
  httpTransport: HttpTransport,
  jsonFactory: JsonFactory,
  credentials: Credential,
  appName: String,
) = ServiceInitializerInjected(httpTransport, jsonFactory, credentials, appName)

fun createDiServiceInitializer(di: DI) = ServiceInitializerDi(di)

interface ServiceInitializer {
  /**
   * ### Create the Google [Drive] service.
   *
   * @return[Drive] The newly created sheets service.
   */
  fun createDrive(): Drive

  /**
   * ### Create the [com.google.api.services.docs.v1.Docs] service.
   *
   * @return[Docs] The newly created [Docs] service.
   */
  fun createDocs(): Docs

  /**
   * ### Create the Google [Sheets] service.
   *
   * @return[Sheets] The newly created sheets service.
   */
  fun createSheets(): Sheets
  fun createCalendar(): Calendar
}

class ServiceInitializerInjected(
  private val httpTransport: HttpTransport,
  private val jsonFactory: JsonFactory,
  private val credentials: Credential,
  private val appName: String,
) : ServiceInitializer {
  /**
   * ### Create the Google [Drive] service.
   *
   * @return[Drive] The newly created sheets service.
   */
  override fun createDrive(): Drive = Drive.Builder(httpTransport, jsonFactory, credentials)
    .setApplicationName(appName)
    .build()

  /**
   * ### Create the [com.google.api.services.docs.v1].[Docs] service.
   *
   * @return[Docs] The newly created [Docs] service.
   */
  override fun createDocs(): Docs = Docs.Builder(httpTransport, jsonFactory, credentials)
    .setApplicationName(appName)
    .build()

  /**
   * ### Create the Google [Sheets] service.
   *
   * @return[Sheets] The newly created sheets service.
   */
  override fun createSheets(): Sheets = Sheets.Builder(httpTransport, jsonFactory, credentials)
    .setApplicationName(appName)
    .build()

  override fun createCalendar(): Calendar = Calendar.Builder(httpTransport, jsonFactory, credentials)
    .setApplicationName(appName)
    .build()

}

class ServiceInitializerDi(override val di: DI) : ServiceInitializer, DIAware {
  private val httpTransport: HttpTransport by instance()
  private val jsonFactory: JsonFactory by instance()
  private val credentials: Credential by instance()
  private val applicationName: String by constant()

  /**
   * ### Create the Google [Drive] service.
   *
   * @return[Drive] The newly created sheets service.
   */
  override fun createDrive(): Drive = Drive.Builder(httpTransport, jsonFactory, credentials)
    .setApplicationName(applicationName)
    .build()

  /**
   * ### Create the [com.google.api.services.docs.v1].[Docs] service.
   *
   * @return[Docs] The newly created [Docs] service.
   */
  override fun createDocs(): Docs = Docs.Builder(httpTransport, jsonFactory, credentials)
    .setApplicationName(applicationName)
    .build()

  /**
   * ### Create the Google [Sheets] service.
   *
   * @return[Sheets] The newly created sheets service.
   */
  override fun createSheets(): Sheets = Sheets.Builder(httpTransport, jsonFactory, credentials)
    .setApplicationName(applicationName)
    .build()

  override fun createCalendar(): Calendar = Calendar.Builder(httpTransport, jsonFactory, credentials)
    .setApplicationName(applicationName)
    .build()

}
