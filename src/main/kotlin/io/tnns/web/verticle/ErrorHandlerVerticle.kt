package io.tnns.web.verticle

import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext


class ErrorHandlerVerticle(private val router: Router) : BaseVerticle() {
  override fun start() {
    super.start()

    router["/*"].failureHandler { ctx: RoutingContext ->
      val statusCode: Int = ctx.statusCode()
      val message = ctx.failure().message

      val data = JsonObject()
        .put("code", statusCode)
        .put("message", message)

      ctx
        .response()
        .setStatusCode(statusCode)
        .end(data.encodePrettily())
    }
  }
}
