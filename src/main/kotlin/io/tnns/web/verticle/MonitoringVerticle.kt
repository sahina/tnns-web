package io.tnns.web.verticle

import io.vertx.ext.healthchecks.HealthCheckHandler
import io.vertx.ext.healthchecks.Status
import io.vertx.ext.web.Router

class MonitoringVerticle(private val router: Router) : BaseVerticle() {
  override fun start() {
    super.start()

    val handler = HealthCheckHandler.create(vertx)

    handler.register("health") { promise ->
      promise.complete(Status.OK())
    }

    router.get("/health").handler(handler)
  }
}
