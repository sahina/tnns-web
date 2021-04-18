package io.tnns.web.verticle

import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.coroutines.CoroutineVerticle

class PlayerVerticle : CoroutineVerticle() {
  override suspend fun start() {
    super.start()

    vertx
      .eventBus()
      .consumer<JsonObject>("player.ranking")
      .handler(this::handler)
  }

  private fun handler(message: Message<JsonObject>) {
    val playerId = message.body().getString("playerId")

    val data = json {
      "playerId" to playerId
      "ranking" to 123
    }

    message.reply(data)
  }
}
