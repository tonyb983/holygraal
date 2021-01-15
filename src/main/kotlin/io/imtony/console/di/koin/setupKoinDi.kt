package io.imtony.console.di.koin

import io.imtony.console.Const
import io.imtony.console.Resources
import org.koin.core.context.startKoin

fun setupKoinDi(consoleLog: Boolean) = startKoin {
    if (consoleLog) {
      printLogger()
    }
    Const.bindToKoin(this)
    Resources.bindToKoin(this)
    //fileProperties()
    modules(consoleAppModule)
  }

