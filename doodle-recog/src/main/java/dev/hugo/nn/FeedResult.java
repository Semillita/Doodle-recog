package dev.hugo.nn;

public record FeedResult<T>(FeedCache cache, T result) {}
