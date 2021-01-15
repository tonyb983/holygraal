@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.imtony.console

import org.kodein.di.DI
import org.kodein.di.with
import org.koin.core.KoinApplication

object Resources {
  private const val root = "/"

  object GCreds {
    private const val base = "${root}gcreds"
    object Credentials {
      const val Path = "$base/credentials.json"
      const val Name = "credsFile"
    }
  }

  fun bindToKodein(builder: DI.Builder) {
    builder.constant(tag = GCreds.Credentials.Name) with GCreds.Credentials.Path
  }

  fun bindToKoin(koinApp: KoinApplication) {
    koinApp.properties(mapOf(GCreds.Credentials.Name to GCreds.Credentials.Path))
  }
}
