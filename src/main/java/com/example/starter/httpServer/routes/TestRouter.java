package com.example.starter.httpServer.routes;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class TestRouter implements IRouter {
  @Override
  public Router registerRoutes(Vertx vertx) {
    final Router router = Router.router(vertx);

    router.post("/test").handler(event -> {
      event
        .response()
        .setStatusCode(200)
        .end(new JsonObject()
          .put("test", "ok")
          .toString());
    });

    return router;
  }
}
