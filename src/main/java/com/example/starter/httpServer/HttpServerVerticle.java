package com.example.starter.httpServer;

import com.example.starter.httpServer.routes.TestRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import org.jboss.logging.Logger;

public class HttpServerVerticle extends AbstractVerticle {
  private static final Logger logger = Logger.getLogger(HttpServerVerticle.class);

  private HttpServer httpServer;

  @Override
  public void start() throws Exception {
    final Router mainRouter = Router.router(vertx);
    mainRouter.route()
      .handler(BodyHandler.create())
      .handler(LoggerHandler.create(LoggerFormat.DEFAULT))
      .handler(CorsHandler.create())
      .handler(event -> {
        event.response()
          .putHeader("Content-Type", "application/json")
          .putHeader("Accept-Encoding", "gzip,deflate");
        event.next();
      });

    mainRouter
      .mountSubRouter("/api", new TestRouter().registerRoutes(vertx));

    mainRouter
      .errorHandler(404, event -> {
        final int statusCode = event.statusCode();
        event
          .response()
          .setStatusCode(statusCode)
          .end();
      })
      .errorHandler(500, event -> {
        final int statusCode = event.statusCode();
        event
          .response()
          .setStatusCode(statusCode)
          .end(new JsonObject()
            .put("error", new JsonObject()
              .put("status", statusCode)
              .put("message", event.failure().getMessage()))
            .toString());
      });

    httpServer = vertx.createHttpServer();
    httpServer
      .requestHandler(mainRouter)
      .listen(4000);
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    httpServer.close().onComplete(event -> {
      logger.debug("server shutdown");
      stopPromise.complete();
    });
  }
}
