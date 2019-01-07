package com.scitrader.marketdataserver.transport;

import com.scitrader.marketdataserver.common.Utility.Guard;
import com.scitrader.marketdataserver.common.MarketDataServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.TriConsumer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.function.Consumer;

public class SciTraderWebsocketClient extends WebSocketClient {

  private static final Logger Log = LogManager.getLogger(SciTraderWebsocketClient.class );

  private final Consumer<ServerHandshake> onOpen;
  private final Consumer<String> onMessage;
  private final TriConsumer<Integer, String, Boolean> onClose;
  private final Consumer<Exception> onError;

  public SciTraderWebsocketClient (URI serverUri,
                                   Consumer<ServerHandshake> onOpen,
                                   Consumer<String> onMessage,
                                   TriConsumer<Integer, String, Boolean> onClose,
                                   Consumer<Exception> onError) {

    super(serverUri);

    Guard.assertNotNull(serverUri, "Server URI cannot be null");
    Guard.assertNotNull(onOpen, "OnOpen consumer cannot be null");
    Guard.assertNotNull(onMessage, "OnMessage consumer cannot be null");
    Guard.assertNotNull(onClose, "OnClose consumer cannot be null");
    Guard.asseartNotNull(onError, "OnError consumer cannot be null");

    this.onOpen = onOpen;
    this.onMessage = onMessage;
    this.onClose = onClose;
    this.onError = onError;
  }

  @Override
  public void connect() {
    try{
      super.connect();
    }
    catch (Exception ex){
      throw new MarketDataServerException();
    }
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    this.onOpen.accept(handshakedata);
  }

  @Override
  public void onMessage(String message) {
    this.onMessage.accept(message);
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    this.onClose.accept(code, reason, remote);
  }

  @Override
  public void onError(Exception ex) {
    this.onError.accept(ex);
  }
}
