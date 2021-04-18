package io.tnns.web.verticle

import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class LandingVerticle(private val router: Router) : BaseVerticle() {
  override fun start() {
    super.start()

    router.get("/").handler(this::handler)
  }

  private fun handler(ctx: RoutingContext) {
    ctx.response().end("tnns-io")
  }
}
