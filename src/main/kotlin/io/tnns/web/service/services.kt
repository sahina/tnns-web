package io.tnns.web.service

import io.tnns.web.model.PlayerModel
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.Vertx

class PlayerServiceImpl(var vertx: Vertx, var address: String) : PlayerService {
  override fun playerByAlias(alias: String, handler: Handler<AsyncResult<PlayerModel>>) {
    handler.handle(Future.succeededFuture(PlayerModel("roger")))
  }
}


@ProxyGen
@VertxGen
interface PlayerService {
  fun playerByAlias(alias: String, handler: Handler<AsyncResult<PlayerModel>>)
}

object PlayerServiceFactory {
  private const val ADDRESS = "player.service"

  fun create(vertx: Vertx): PlayerService {
    return PlayerServiceImpl(vertx, ADDRESS)
  }

  fun createService(vertx: Vertx, address: String): PlayerService {
    return PlayerServiceImpl(vertx, address)
  }
}

//
// kapt produces error:
//  Could not generate model for Companion: type io.tnns.web.service.PlayerService.Companion
//  is not legal for use for a constant type in code generation

//@ProxyGen
//@VertxGen
//interface PlayerService {
//  @Fluent
//  fun playerByAlias(alias: String, handler: Handler<AsyncResult<Player>>): PlayerService
//
//  @GenIgnore
//  companion object {
//    private const val ADDRESS = "player.service"
//
//    fun create(vertx: Vertx): PlayerService {
//      return PlayerServiceImpl(vertx, ADDRESS)
//    }
//
//    fun createService(vertx: Vertx, address: String): PlayerService {
//      return PlayerServiceImpl(vertx, address)
//    }
//  }
//}
