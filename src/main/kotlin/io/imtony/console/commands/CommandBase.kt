package io.imtony.console.commands

import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.factory
import org.kodein.di.instance

class ConsoleController(override val di: DI) : DIAware {
  private val commandSet: Set<ConsoleCommand> by instance()
  private val commandFactory: (CommandQuery) -> ConsoleCommand by factory()
}

data class CommandQuery(val context: CommandContext, val input: String, val args: String)

class CommandContext(override val di: DI) : DIAware {
  val controller: ConsoleController by instance()
}

interface ConsoleCommand {
  val inputText: String
  val helpText: String
  val aliases: Collection<String>
    get() = emptyList()

  fun execute(ctx: CommandContext, args: String)
}

abstract class CommandBase : ConsoleCommand
