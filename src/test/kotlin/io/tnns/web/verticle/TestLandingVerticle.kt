package io.tnns.web.verticle

import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Landing pages integration tests")
class TestLandingVerticle {

  @BeforeEach
  fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
    val router = Router.router(vertx)

    vertx.deployVerticle(LandingVerticle(router), testContext.succeeding { testContext.completeNow() })
  }

  @Test
  fun `landing page should return 200`(vertx: Vertx, testContext: VertxTestContext) {
    given(requestSpec())
      .get("/")
      .then()
      .assertThat()
      .statusCode(200)

    testContext.completeNow()
  }

  @Test
  fun `health page should return 200`(vertx: Vertx, testContext: VertxTestContext) {
    given(requestSpec())
      .get("/health")
      .then()
      .assertThat()
      .statusCode(200)

    testContext.completeNow()
  }

  @Test
  fun `config page should return 200`(vertx: Vertx, testContext: VertxTestContext) {
    given(requestSpec())
      .get("/config")
      .then()
      .assertThat()
      .contentType(ContentType.JSON)
      .statusCode(200)

    testContext.completeNow()
  }

  private fun requestSpec(): RequestSpecification {
    return RequestSpecBuilder()
      .setBaseUri("http://localhost:8888")
      .build()
  }
}
