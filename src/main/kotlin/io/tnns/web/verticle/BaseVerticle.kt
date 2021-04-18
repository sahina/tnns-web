package io.tnns.web.verticle

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject


open class BaseVerticle : AbstractVerticle() {
  lateinit var configRetriever: ConfigRetriever

  override fun start() {
    super.start()

    setupConfig()
  }

  private fun setupConfig() {
    val fileStore = ConfigStoreOptions()
      .setType("file")
      .setConfig(JsonObject().put("path", "conf/config.local.json"))

    val options = ConfigRetrieverOptions()
      .addStore(fileStore)

    configRetriever = ConfigRetriever.create(vertx, options)
  }
}
