package io.imtony.console

import im.tony.google.extensions.drive.GoogleMimeTypes
import io.imtony.console.di.kodein.consoleAppModule
import io.imtony.console.di.kodein.setupKodeinDi
import io.imtony.console.di.koin.setupKoinDi
import io.imtony.console.google.services.GoogleDriveService
import io.imtony.console.google.services.setDefaults
import org.kodein.di.DI
import org.kodein.di.conf.global
import org.kodein.di.direct
import org.kodein.di.instance
import org.kodein.type.TypeToken

const val USE_KOIN = true

// Following tutorial at:
// https://developers.google.com/drive/api/v3/quickstart/java
fun main(args: Array<String>) {
  val container = setupKoinDi(true)
  val drive: GoogleDriveService = container.koin.getOrNull<GoogleDriveService>() ?: throw RuntimeException("Unable to load GoogleDriveService")

  print("Enter search term: ")
  val input = readLine()
  val found = drive.service.files().list().setDefaults().setQ(input).execute().files
  println("Found ${found.size} Files:")
  println("\t" + found.joinToString("\n\t") { "${it.name} - ${GoogleMimeTypes.fromMime(it.mimeType)} (ID: ${it.id})" })
}
