module org.nkjmlab.util.core {
  requires transitive java.sql;
  requires transitive java.net.http;
  requires transitive java.desktop;
  requires transitive jakarta.servlet;
  requires transitive java.management;
  requires transitive org.apache.logging.log4j;
  requires transitive org.apache.logging.log4j.core;
  requires transitive org.apache.commons.csv;
  requires transitive org.apache.commons.lang3;
  requires transitive com.fasterxml.jackson.databind;
  requires transitive com.fasterxml.jackson.annotation;
  requires transitive com.fasterxml.jackson.datatype.jsr310;
  requires transitive com.h2database;


  exports org.nkjmlab.util.commons.csv;
  exports org.nkjmlab.util.h2;
  exports org.nkjmlab.util.jackson;
  exports org.nkjmlab.util.jakarta.servlet;
  exports org.nkjmlab.util.javax.imageio;
  exports org.nkjmlab.util.jsonrpc;
  exports org.nkjmlab.util.log4j;
  exports org.nkjmlab.util.java;

  exports org.nkjmlab.util.java.collections;
  exports org.nkjmlab.util.java.concurrent;
  exports org.nkjmlab.util.java.function;
  exports org.nkjmlab.util.java.io;
  exports org.nkjmlab.util.java.json;
  exports org.nkjmlab.util.java.lang;
  exports org.nkjmlab.util.java.lang.reflect;
  exports org.nkjmlab.util.java.logging;
  exports org.nkjmlab.util.java.math;
  exports org.nkjmlab.util.java.net;
  exports org.nkjmlab.util.java.security;
  exports org.nkjmlab.util.java.stream;
  exports org.nkjmlab.util.java.time;
  exports org.nkjmlab.util.java.web;
  exports org.nkjmlab.util.java.zip;


}
