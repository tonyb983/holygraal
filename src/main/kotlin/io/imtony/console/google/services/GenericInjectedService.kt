package io.imtony.console.google.services

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient
import org.kodein.di.*

interface GoogleService<TService : AbstractGoogleJsonClient> {
  val service: TService
}

abstract class GenericInjectedService<TService : AbstractGoogleJsonClient>(lazyCreator: Lazy<TService>) : GoogleService<TService> {
  private val service_: TService by lazyCreator
  override val service: TService
    get() = service_
}
