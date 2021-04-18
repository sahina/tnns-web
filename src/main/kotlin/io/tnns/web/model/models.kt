package io.tnns.web.model

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

@DataObject
class PlayerModel(val alias: String) {
  fun toJson(): JsonObject {
    return JsonObject().put("alias", alias)
  }
}
