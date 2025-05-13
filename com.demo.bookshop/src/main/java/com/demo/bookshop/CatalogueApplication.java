package com.demo.bookshop;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CatalogueApplication {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}
