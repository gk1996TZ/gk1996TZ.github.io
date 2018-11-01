package com.litang.utils;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class MJedisUtil {
	/**最大的活跃数量*//*
	static final int maxTotal=10;
	*//**最大空闲连接数*//*
	static final int maxIdle=6;
	*//**最大的等待毫秒数*//*
	static final long maxWaitMillis=1000*5;
	*//**最小空闲连接数*//*
	static final int minIdle=5;
	*//**是否启用先进先出*//*
	static final boolean lifo=true;
	*//**逐出连接的最小空闲时间 默认1800000毫秒(30分钟)*//*
	static final long minEvictableIdleTimeMillis=1000*30*60;
	*//**redis地址*//*
	static final String host="127.0.0.1";
	*//**配置jedis连接池特性*//*
	static JedisPool jedisPool;
	public Jedis jedis;
	static {
		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setLifo(lifo);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMinIdle(minIdle);
		jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		jedisPool=new JedisPool(jedisPoolConfig, host);
	}
	public Jedis getJedis() {
		return jedisPool.getResource();
	}
	*//**关闭连接池*//*
	public void closeJedisPool() {
		jedisPool.close();
	}*/
}
