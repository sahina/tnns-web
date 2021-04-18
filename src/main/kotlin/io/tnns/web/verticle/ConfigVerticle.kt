package io.tnns.web.verticle

import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class ConfigVerticle(private val router: Router) : BaseVerticle() {
  override fun start() {
    super.start()

    router.get("/config").handler(this::handler)
  }

  private fun handler(ctx: RoutingContext) {
    configRetriever.getConfig { config ->
      if (config.succeeded()) {
        ctx
          .response()
          .putHeader("content-type", "application/json")
          .end(config.result().encodePrettily())
      } else {
        ctx.fail(100)
      }
    }
  }
}
