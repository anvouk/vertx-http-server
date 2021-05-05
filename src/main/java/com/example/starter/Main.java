package com.example.starter;

import com.example.starter.httpServer.HttpServerVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.jboss.logging.Logger;

public class Main {
  private static final Logger logger = Logger.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("starting app");
    final Vertx vertx = Vertx.vertx();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      logger.info("shutting down app");
      vertx.deploymentIDs().forEach(vertx::undeploy);
      vertx.close();
      LogManager.shutdown();
    }));

    vertx.deployVerticle(HttpServerVerticle.class, new DeploymentOptions());
  }
}
