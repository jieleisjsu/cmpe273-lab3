package edu.sjsu.cmpe.cache.client;

import java.util.*;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public class Client {
	private static final String SERVER_3000 = "http://localhost:3000";
	private static final String SERVER_3001 = "http://localhost:3001";
	private static final String SERVER_3002 = "http://localhost:3002";
	
    public static void main(String[] args) throws Exception {
        
        String[] values = {"0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        
        List<DistributedCacheService> servers = new ArrayList<DistributedCacheService>();
        servers.add(new DistributedCacheService(SERVER_3000));
        servers.add(new DistributedCacheService(SERVER_3001));
        servers.add(new DistributedCacheService(SERVER_3002));
        
        System.out.println("Putting values to server...");
        for(int putKey = 1; putKey < 11; putKey++){
        	int bucket = Hashing.consistentHash(Hashing.md5().hashLong(putKey), servers.size());
        	servers.get(bucket).put(putKey, values[putKey]);
        	System.out.println(" Putting the value - " + values[putKey] + " with key - " + putKey + " to server - " + (bucket+1));
        }
        
        System.out.println("Getting values from server...");
        for(int getKey = 1; getKey < 11; getKey++){
        	int bucket = Hashing.consistentHash(Hashing.md5().hashLong(getKey), servers.size());
        	System.out.println(" Getting the value - " + servers.get(bucket).get(getKey) + " with key - " + getKey+ " from server - "+ (bucket+1)); 
        }
        
        System.out.println("Caching Done!");
