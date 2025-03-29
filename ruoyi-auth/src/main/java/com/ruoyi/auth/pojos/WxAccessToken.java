package com.ruoyi.auth.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 微信AccessToken响应（线程安全实现）
 */
@Data
public class WxAccessToken {
    private final ReentrantLock lock = new ReentrantLock();

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;

    // 内部记录的时间戳（精确到毫秒）
    private volatile long refreshTime;

    /**
     * 检查token是否过期（预留5分钟缓冲期）
     */
    public boolean isExpired() {
        lock.lock();
        try {
            return System.currentTimeMillis() - refreshTime > (expiresIn - 300) * 1000L;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 更新token时调用
     */
    public void updateToken(String newToken, Long newExpiresIn) {
        lock.lock();
        try {
            this.accessToken = newToken;
            this.expiresIn = newExpiresIn;
            this.refreshTime = System.currentTimeMillis();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取剩余有效期（秒）
     */
    public int getRemainingSeconds() {
        lock.lock();
        try {
            long elapsed = (System.currentTimeMillis() - refreshTime) / 1000;
            return (int) (expiresIn - elapsed);
        } finally {
            lock.unlock();
        }
    }
}