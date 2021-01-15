package io.imtony.console.di.kodein

import org.kodein.di.DI
import org.kodein.di.conf.global

fun setupKodeinDi() {
  DI.global.addImport(consoleAppModule)
}
