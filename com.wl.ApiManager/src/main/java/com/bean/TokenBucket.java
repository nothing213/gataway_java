package com.bean;

import java.util.concurrent.atomic.AtomicLong;

public class TokenBucket
{
    private final long capacity;
    private final long refillTokensPerOneMillis;
    private AtomicLong availableTokens;
    private AtomicLong lastRefillTimestamp;
    public TokenBucket(long capacity, long refillTokensPerOneMillis) {
        this.capacity = capacity;
        this.refillTokensPerOneMillis = refillTokensPerOneMillis;
        this.availableTokens = new AtomicLong(capacity);
        this.lastRefillTimestamp = new AtomicLong(System.currentTimeMillis());
    }
    public synchronized boolean tryConsume(long numTokens)
    {        refill();
        if (availableTokens.get() >= numTokens)
        {
            availableTokens.addAndGet(-numTokens);
            return true;
        }
        return false;
    }
    private void refill() {
        long currentTimeMillis = System.currentTimeMillis();
        long timeSinceLastRefill = currentTimeMillis - lastRefillTimestamp.get();
        long tokensToRefill = timeSinceLastRefill * refillTokensPerOneMillis;
        if (tokensToRefill > 0) {
            availableTokens.addAndGet(tokensToRefill);
            availableTokens.set(Math.min(capacity, availableTokens.get()));
            lastRefillTimestamp.set(currentTimeMillis);
        }
    }
}