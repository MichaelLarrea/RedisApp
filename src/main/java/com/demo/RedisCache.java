package com.demo;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import java.util.Arrays;
import java.util.List;

public class RedisCache {
    private Jedis jedis;
    private Gson gson;

    public RedisCache() {
        jedis = new Jedis("localhost", 6379);
        gson = new Gson();
    }

    public void saveEmployeeData(int empNo, List<EmployeeInfo> data) {
        long startSerialize = System.nanoTime();
        String json = gson.toJson(data);
        long endSerialize = System.nanoTime();

        long startRedis = System.nanoTime();
        jedis.set("employee:" + empNo, json);
        long endRedis = System.nanoTime();

        System.out.printf("[CACHE WRITE] Serialización : %,d ns (%.3f ms)%n",
                (endSerialize - startSerialize),
                (endSerialize - startSerialize) / 1_000_000.0);

        System.out.printf("[CACHE WRITE] Guardado Redis: %,d ns (%.3f ms)%n",
                (endRedis - startRedis),
                (endRedis - startRedis) / 1_000_000.0);

        System.out.printf("[CACHE WRITE] Total         : %,d ns (%.3f ms)%n",
                (endRedis - startSerialize),
                (endRedis - startSerialize) / 1_000_000.0);
    }

    public List<EmployeeInfo> getEmployeeData(int empNo) {
        long startRedis = System.nanoTime();
        String json = jedis.get("employee:" + empNo);
        long endRedis = System.nanoTime();

        System.out.printf("[CACHE READ] Lectura Redis  : %,d ns (%.3f ms)%n",
                (endRedis - startRedis),
                (endRedis - startRedis) / 1_000_000.0);

        if (json != null) {
            long startDeserialize = System.nanoTime();
            EmployeeInfo[] arr = gson.fromJson(json, EmployeeInfo[].class);
            List<EmployeeInfo> result = Arrays.asList(arr);
            long endDeserialize = System.nanoTime();

            System.out.printf("[CACHE READ] Deserialización: %,d ns (%.3f ms)%n",
                    (endDeserialize - startDeserialize),
                    (endDeserialize - startDeserialize) / 1_000_000.0);

            System.out.printf("[CACHE READ] Total (HIT)    : %,d ns (%.3f ms)%n",
                    (endDeserialize - startRedis),
                    (endDeserialize - startRedis) / 1_000_000.0);

            return result;
        }

        System.out.printf("[CACHE READ] Total (MISS)   : %,d ns (%.3f ms)%n",
                (endRedis - startRedis),
                (endRedis - startRedis) / 1_000_000.0);

        return null;
    }
}