# DO NOT EDIT THIS FILE - See: https://eclipse.dev/jetty/documentation/

[description]
Adds the FastCGI implementation to the classpath.

[depend]
servlet
client

[lib]
lib/jetty-proxy-${jetty.version}.jar
lib/fcgi/*.jar

[ini-template]
## For configuration of FastCGI contexts, see
## https://eclipse.dev/jetty/documentation/
