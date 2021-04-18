package io.tnns.web.verticle

import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.CoroutineVerticle

class ApiVerticle(private val router: Router) : CoroutineVerticle() {
  override suspend fun start() {
    super.start()

    router
      .get("/api/players")
      .handler(this::handler)
  }

  private fun handler(ctx: RoutingContext) {
    ctx.response().end("players")
  }
}
