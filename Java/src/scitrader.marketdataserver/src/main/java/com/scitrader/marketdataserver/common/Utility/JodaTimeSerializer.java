package com.scitrader.marketdataserver.common.Utility;

import com.jsoniter.spi.JsonException;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.Decoder;
import com.jsoniter.spi.Encoder;
import com.jsoniter.spi.JsoniterSpi;
import org.joda.time.DateTime;

import java.io.IOException;

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
        stream.writeVal(((DateTime)obj).toString(DateUtil.getTickFormatter()));
      }

      @Override
      public Any wrap(Object obj) {
        return Any.wrap(((DateTime)obj).toString(DateUtil.getTickFormatter()));
      }
    });
    JsoniterSpi.registerTypeDecoder(DateTime.class, new Decoder() {
      @Override
      public Object decode(JsonIterator iter) throws IOException {
        try {
          return DateUtil.getTickFormatter().parseDateTime(iter.readString());
        } catch (Exception e) {
          throw new JsonException(e);
        }
      }
    });
  }
}
