package io.tnns.web

import io.tnns.web.verticle.*
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router

class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val router = Router.router(vertx)

    vertx.deployVerticle(WebServerVerticle(router))
    vertx.deployVerticle(LandingVerticle(router))
    vertx.deployVerticle(ConfigVerticle(router))
    vertx.deployVerticle(ErrorHandlerVerticle(router))
    vertx.deployVerticle(MonitoringVerticle(router))
    vertx.deployVerticle(PlayerVerticle())
    vertx.deployVerticle(ApiVerticle(router))
  }
}
