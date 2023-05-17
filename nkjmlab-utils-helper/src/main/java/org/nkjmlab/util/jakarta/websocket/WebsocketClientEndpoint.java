package org.nkjmlab.util.jakarta.websocket;

import java.util.function.BiConsumer;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;

@ClientEndpoint
public class WebsocketClientEndpoint {

  private final Consumer<Session> onOpenHandler;
  private final BiConsumer<String, Session> onMessageHandler;
  private final BiConsumer<CloseReason, Session> onCloseHandler;
  private final BiConsumer<Throwable, Session> onErrorHandler;

  public WebsocketClientEndpoint(Consumer<Session> onOpenHandler,
      BiConsumer<String, Session> onMessageHandler, BiConsumer<CloseReason, Session> onCloseHandler,
      BiConsumer<Throwable, Session> onErrorHandler) {
    this.onOpenHandler = onOpenHandler;
    this.onMessageHandler = onMessageHandler;
    this.onCloseHandler = onCloseHandler;
    this.onErrorHandler = onErrorHandler;
  }

  @OnOpen
  public void onOpen(Session session) {
    onOpenHandler.accept(session);
  }

  @OnMessage
  public void onMessage(String message, Session session) {
    onMessageHandler.accept(message, session);
  }

  @OnClose
  public void onClose(CloseReason closeReason, Session session) {
    onCloseHandler.accept(closeReason, session);
  }

  @OnError
  public void onError(Throwable th, Session session) {
    onErrorHandler.accept(th, session);
  }


}
