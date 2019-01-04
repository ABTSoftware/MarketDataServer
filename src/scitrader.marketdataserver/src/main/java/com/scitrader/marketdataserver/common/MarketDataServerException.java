package com.scitrader.marketdataserver.common;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MarketDataServerException extends RuntimeException {

        public MarketDataServerException() {
                super();
        }

        public MarketDataServerException(String message) {
                super(message);
        }

        public MarketDataServerException(String message, Exception inner){
                super(message, inner);
        }

        public MarketDataServerException(Exception inner){
                super(inner);
        }
}

