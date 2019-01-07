# Developer Setup for the Kotlin MarketDataServer example

http://guides.micronaut.io/creating-your-first-micronaut-app-kotlin/guide/index.html

1.) Download and install the Micronaut framework 
https://docs.micronaut.io/snapshot/guide/index.html#buildCLI

 * Download the latest binary from Micronaut Website
 * Extract the binary to appropriate location (For example: C:/micronaut)
 * Create an environment variable MICRONAUT_HOME which points to the installation directory i.e. C:/micronaut
 * Update the PATH environment variable, append %MICRONAUT_HOME%\bin.
 * You should now be able to run the Micronaut CLI from the command prompt as follows:
 * > mn .. 
 
 
2.) mn create-app example.micronaut.complete --features=kotlin

 * Create a Kotlin Micronaut app using the Micronaut Command Line Interface.
 * > mn create-app example.micronaut.complete --features=kotlin
 