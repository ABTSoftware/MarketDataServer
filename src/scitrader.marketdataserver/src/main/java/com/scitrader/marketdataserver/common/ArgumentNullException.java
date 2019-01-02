package com.scitrader.marketdataserver.common;

public class ArgumentNullException extends RuntimeException {
        public ArgumentNullException() {
                super();
        }

        public ArgumentNullException(String message) {
                super(message);
        }

        public ArgumentNullException(String message, Exception inner){
                super(message, inner);
        }

        public ArgumentNullException(Exception inner){
                super(inner);
        }
}
