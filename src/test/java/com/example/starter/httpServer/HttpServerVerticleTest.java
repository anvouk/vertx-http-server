package com.example.starter.httpServer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class HttpServerVerticleTest {
  private Vertx vertx;

  @BeforeEach
  public void setUp() {
    vertx = Vertx.vertx();
  }

  @AfterEach
  public void tearDown() {
    vertx.deploymentIDs().forEach(vertx::undeploy);
    vertx.close();
  }

  @Test
  public void deployVerticle() {
    VertxTestContext testContext = new VertxTestContext();

    vertx.deployVerticle(HttpServerVerticle.class, new DeploymentOptions())
      .onComplete(testContext.succeedingThenComplete());
  }
}
