package com.scitrader.marketdataserver.common;

import com.jsoniter.spi.JsonException;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.Decoder;
import com.jsoniter.spi.Encoder;
import com.jsoniter.spi.JsoniterSpi;
import com.scitrader.marketdataserver.exchange.bitmex.Tick;
import org.joda.time.DateTime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JodaTimeSerializer {

  private static boolean isEnabled;

  public static synchronized void enable() {
    if (JodaTimeSerializer.isEnabled == true) {
      throw new JsonException("JdkDatetimeSupport.enable can only be called once");
    }
    JodaTimeSerializer.isEnabled = true;
    JsoniterSpi.registerTypeEncoder(DateTime.class, new Encoder.ReflectionEncoder() {
      @Override
      public void encode(Object obj, JsonStream stream) throws IOException {
        stream.writeVal(((DateTime)obj).toString(Tick.formatter));
      }

      @Override
      public Any wrap(Object obj) {
        return Any.wrap(((DateTime)obj).toString(Tick.formatter));
      }
    });
    JsoniterSpi.registerTypeDecoder(DateTime.class, new Decoder() {
      @Override
      public Object decode(JsonIterator iter) throws IOException {
        try {
          return Tick.formatter.parseDateTime(iter.readString());
        } catch (Exception e) {
          throw new JsonException(e);
        }
      }
    });
  }
}
