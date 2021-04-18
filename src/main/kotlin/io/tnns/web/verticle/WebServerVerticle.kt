package io.tnns.web.verticle

import io.vertx.core.Promise
import io.vertx.ext.web.Router

class WebServerVerticle(private val router: Router) : BaseVerticle() {
  override fun start(startPromise: Promise<Void>) {
    super.start()

    configRetriever
      .config
      .onSuccess { data ->
        val port = data.getInteger("http.port")

        vertx
          .createHttpServer()
          .requestHandler(router)
          .listen(port)
          .onFailure { http -> startPromise.fail(http.cause) }
          .onSuccess {
            startPromise.complete()
            println("HTTP server started on port $port")
          }
      }

  }
}
