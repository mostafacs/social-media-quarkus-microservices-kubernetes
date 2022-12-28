package org.social.caching;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.map.IMap;
import org.social.form.PostForm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

/**
 * @Author Mostafa
 * On 12/28/22
 */
@ApplicationScoped
public class HazelcastPostCacheManager implements PostCacheManager {
    @Inject
    HazelcastInstance hazelcast;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public boolean contains(Long postId) {
        return hazelcast.getMap(POSTS_CACHE_NAME).containsKey(postId);
    }

    @Override
    public void cachePost(PostForm post) throws Exception{
//        EvictionConfig ec = new EvictionConfig();
//        ec.setMaxSizePolicy(MaxSizePolicy.USED_NATIVE_MEMORY_PERCENTAGE);
//        ec.setSize(90);
//        MapConfig mc = new MapConfig();
//        mc.setEvictionConfig(ec);
//        hazelcast.getConfig().setMapConfigs(Map.of(POSTS_CACHE_NAME, mc));
        // hazelcast.getConfig().setMapConfigs()
        IMap<Long, HazelcastJsonValue> map = hazelcast.getMap(POSTS_CACHE_NAME);
        map.put(post.getId(), new HazelcastJsonValue(objectMapper.writeValueAsString(post)));
    }

    @Override
    public PostForm getPost(Long postId) throws Exception{
        IMap<Long, HazelcastJsonValue> map = hazelcast.getMap(POSTS_CACHE_NAME);
        HazelcastJsonValue value = map.get(postId);
        if(value != null) {
            return objectMapper.readValue(value.getValue(), PostForm.class);
        }
        return null;
    }
}
