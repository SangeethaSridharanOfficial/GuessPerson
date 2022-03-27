package com.helper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FutureHelper {
    private FutureHelper(){}

    public static List<Object> awaitFutureAll(List<CompletableFuture<Object>> futures ){
        return Stream.of(futures.toArray(new CompletableFuture[futures.size()]))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
