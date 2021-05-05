package com.example.starter.httpServer.routes;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public interface IRouter {
  Router registerRoutes(Vertx vertx);
}
