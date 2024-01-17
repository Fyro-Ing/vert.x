/*
 * Copyright (c) 2011-2019 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */

package io.vertx.core.http;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.core.impl.ContextInternal;
import io.vertx.core.net.ClientSSLOptions;
import io.vertx.core.net.HostAndPort;
import io.vertx.core.net.SocketAddress;

import java.util.concurrent.TimeUnit;

/**
 * The API to interacts with an HTTP server.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen(concrete = false)
public interface HttpClient {

  /**
   * Create an HTTP request to send to the server with the default host and port of the client.
   *
   * @return a future notified when the request is ready to be sent
   */
  default Future<HttpClientRequest> request() {
    return request(new RequestOptions());
  }

  /**
   * Create an HTTP request to send to the server.
   *
   * @param options    the request options
   * @return a future notified when the request is ready to be sent
   */
  Future<HttpClientRequest> request(RequestOptions options);

  /**
   * Create an HTTP request to send to the server at the {@code host} and {@code port}.
   *
   * @param method     the HTTP method
   * @param port       the port
   * @param host       the host
   * @param requestURI the relative URI
   * @return a future notified when the request is ready to be sent
   */
  default Future<HttpClientRequest> request(HttpMethod method, int port, String host, String requestURI) {
    return request(new RequestOptions().setMethod(method).setPort(port).setHost(host).setURI(requestURI));
  }

  /**
   * Create an HTTP request to send to the server at the {@code host} and default port.
   *
   * @param method     the HTTP method
   * @param host       the host
   * @param requestURI the relative URI
   * @return a future notified when the request is ready to be sent
   */
  default Future<HttpClientRequest> request(HttpMethod method, String host, String requestURI) {
    return request(new RequestOptions().setMethod(method).setHost(host).setURI(requestURI));
  }

  /**
   * Create an HTTP request to send to the server at the default host and port.
   *
   * @param method     the HTTP method
   * @param requestURI the relative URI
   * @return a future notified when the request is ready to be sent
   */
  default Future<HttpClientRequest> request(HttpMethod method, String requestURI) {
    return request(new RequestOptions().setMethod(method).setURI(requestURI));
  }

  /**
   * Close the client immediately ({@code close(0, TimeUnit.SECONDS}).
   *
   * @return a future notified when the client is closed
   */
  default Future<Void> close() {
    return close(0, TimeUnit.SECONDS);
  }

  /**
   * Initiate the client close sequence.
   *
   * <p> Connections are taken out of service and closed when all inflight requests are processed, client connection are
   * immediately removed from the pool. When all connections are closed the client is closed. When the timeout
   * expires, all unclosed connections are immediately closed.
   *
   * <ul>
   *   <li>HTTP/2 connections will send a go away frame immediately to signal the other side the connection will close</li>
   *   <li>HTTP/1.x client connection will be closed after the current response is received</li>
   * </ul>
   *
   * @return a future notified when the client is closed
   */
  Future<Void> close(long timeout, TimeUnit timeUnit);

}
