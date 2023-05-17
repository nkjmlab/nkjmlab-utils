package org.nkjmlab.util.jakarta.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.function.BiConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nkjmlab.util.java.function.Try;
import org.nkjmlab.util.java.lang.StringFormatter;
import org.nkjmlab.util.java.net.UriUtils;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;

public class WebsocketClientSession {
  private static final Logger log = LogManager.getLogger();

  private final Session session;

  /**
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
      log.error(e);
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
    private Consumer<Session> onOpenHandler = session -> {
      log.debug("onOpen={}", session);
    };
    private BiConsumer<String, Session> onMessageHandler = (message, session) -> {
      log.debug("[{}] onMessage {}", session.getId(), message);
    };

    private BiConsumer<CloseReason, Session> onCloseHandler = (closeReason, session) -> {
      log.debug("[{}] onClose {}", session.getId(), closeReason);
      try {
        session.close();
      } catch (IOException e) {
        log.error(e, e);
      }
    };

    private BiConsumer<Throwable, Session> onErrorHandler = (th, session) -> {
      log.debug("[{}] onError {}", session.getId(), th);
    };
    private URI uri;



    private Builder(String uri) {
      this.uri = UriUtils.of(uri);

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
        WebsocketClientEndpoint endpoint = new WebsocketClientEndpoint(onOpenHandler,
            onMessageHandler, onCloseHandler, onErrorHandler);
        Session session = ContainerProvider.getWebSocketContainer().connectToServer(endpoint, uri);
        if (session.isOpen()) {
          log.debug("[{}] session is open to {}", session.getId(), uri);
        }
        return new WebsocketClientSession(session);
      } catch (DeploymentException | IOException e) {
        log.error(StringFormatter.format("error occurs {}", uri), e);
        throw Try.rethrow(e);
      }
    }

  }



}
