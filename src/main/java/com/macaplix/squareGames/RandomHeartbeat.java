package com.macaplix.squareGames;

import org.springframework.stereotype.Service;

@Service
public class RandomHeartbeat implements HeartbeatSensor {

    @Override
    public int get() {
        return (int) Math.round(Math.random() * 100);
    }
}
