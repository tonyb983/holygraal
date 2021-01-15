package io.imtony.console.commands

import im.tony.google.extensions.drive.GoogleMimeTypes
import io.imtony.console.google.services.GoogleDriveInjectedService
import io.imtony.console.google.services.setDefaults

class DriveFileSearchCommand(private val driveService: GoogleDriveInjectedService) : CommandBase() {
  override val inputText: String = "search"
  override val helpText: String = """
    | Drive File Search
    |   Search through google drive to find a file which matches your query.
    |   
    | USAGE:
    |   search 18851 Violation
    |
    | OPTIONS:
    |   --folders, -f             Search folders only.
    |   --best, -b                Return only first / best result.
    |   --results=Integer, -r     Limit the results returned.
  """.trimMargin()

  override fun execute(ctx: CommandContext, args: String) {
    val files = driveService.service.files().list().setDefaults().setQ(args).execute().files
    println("Found ${files.size} results:")
    println("\t" + files.joinToString("\n\t") { "${it.name} - ${GoogleMimeTypes.fromMime(it.mimeType)} (ID: ${it.id})" })
  }
}
