package io.tnns.web.service

import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Player service integration tests")
class TestPlayerService {

//  @BeforeEach
//  fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
//    val router = Router.router(vertx)
//
//    vertx.deployVerticle(LandingVerticle(router), testContext.succeeding { testContext.completeNow() })
//  }

  @Test
  fun `create player service proxy`(vertx: Vertx, testContext: VertxTestContext) {
    val service = PlayerServiceFactory.create(vertx)

    service
      .playerByAlias("roger") { player ->
        assert(player.succeeded())
        assert(player.result().alias == "roger")

        testContext.completeNow()
      }

  }
}
