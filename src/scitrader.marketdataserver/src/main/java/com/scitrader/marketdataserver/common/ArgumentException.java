package com.scitrader.marketdataserver.common;

public class ArgumentException extends RuntimeException {
        public ArgumentException() {
                super();
        }

        public ArgumentException(String message) {
                super(message);
        }

        public ArgumentException(String message, Exception inner){
                super(message, inner);
        }

        public ArgumentException(Exception inner){
                super(inner);
        }
}
