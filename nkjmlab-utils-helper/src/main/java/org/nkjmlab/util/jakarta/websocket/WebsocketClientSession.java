package org.nkjmlab.util.jakarta.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.BiConsumer;

import org.nkjmlab.sorm4j.internal.util.Try;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;

public class WebsocketClientSession {
  private static final org.apache.logging.log4j.Logger log =
      org.apache.logging.log4j.LogManager.getLogger();

  private final Session session;

  /**
   *
   *
   * <pre>
   * WebsocketClientSession session =
   *     WebsocketClientSession.builder("wss://foo.nkjmlab.org/websocket/connection").open();
   * </pre>
   *
   * @param uri destination uri
   * @return
   */
  public static WebsocketClientSession.Builder builder(String uri) {
    return new Builder(uri);
  }

  public void close() {
    try {
      session.close();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  public void sendMessage(String message) {
    try {
      session.getBasicRemote().sendText(message);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  @Override
  public String toString() {
    return "WebsocketClient [session=" + session + "]";
  }

  public Session getSession() {
    return session;
  }

  private WebsocketClientSession(Session session) {
    this.session = session;
  }

  public static class Builder {
    private Consumer<Session> onOpenHandler =
        session -> {
          log.debug("onOpen={}", session);
        };
    private BiConsumer<String, Session> onMessageHandler =
        (message, session) -> {
          log.debug("[{}] onMessage {}", session.getId(), message);
        };

    private BiConsumer<CloseReason, Session> onCloseHandler =
        (closeReason, session) -> {
          log.debug("[{}] onClose {}", session.getId(), closeReason);
          try {
            session.close();
          } catch (IOException e) {
            log.error(e, e);
          }
        };

    private BiConsumer<Throwable, Session> onErrorHandler =
        (th, session) -> {
          log.debug("[{}] onError {}", session.getId(), th);
        };
    private URI uri;

    private Builder(String uri) {
      this.uri = toUri(uri);
    }

    private URI toUri(String uri) {
      try {
        return new URI(uri);
      } catch (URISyntaxException e) {
        throw Try.rethrow(e);
      }
    }

    public void setOnOpenHandler(Consumer<Session> onOpenHandler) {
      this.onOpenHandler = onOpenHandler;
    }

    public void setOnMessageHandler(BiConsumer<String, Session> onMessageHandler) {
      this.onMessageHandler = onMessageHandler;
    }

    public void setOnCloseHandler(BiConsumer<CloseReason, Session> onCloseHandler) {
      this.onCloseHandler = onCloseHandler;
    }

    public void setOnErrorHandler(BiConsumer<Throwable, Session> onErrorHandler) {
      this.onErrorHandler = onErrorHandler;
    }

    public WebsocketClientSession open() {
      try {
        WebsocketClientEndpoint endpoint =
            new WebsocketClientEndpoint(
                onOpenHandler, onMessageHandler, onCloseHandler, onErrorHandler);
        Session session = ContainerProvider.getWebSocketContainer().connectToServer(endpoint, uri);
        if (session.isOpen()) {
          log.debug("[{}] session is open to {}", session.getId(), uri);
        }
        return new WebsocketClientSession(session);
      } catch (DeploymentException | IOException e) {
        log.error("error occurs" + uri, e);
        throw Try.rethrow(e);
      }
    }
  }
}
