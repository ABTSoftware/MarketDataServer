# MarketDataServer
Prototype market data server in Java and Kotlin

Not production code, Built as a learning exercise into Java and Kotlin

- Connects to Bitmex websocket via public API 
- Subscribes to ETHUSD and XBTUSD trades (ticks)
- Stores ticks in MongoDB 
- Simple Rest API to fetch ticks and aggregate as OHLC candles by date range


