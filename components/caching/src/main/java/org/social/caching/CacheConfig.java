package org.social.caching;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientConnectionStrategyConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.KubernetesConfig;
import com.hazelcast.config.QueryCacheConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.ShutdownEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

/**
 * @Author Mostafa
 * On 12/2/22
 */
@ApplicationScoped
public class CacheConfig {


    @Produces
    public static HazelcastInstance hazelCaseClient (@ConfigProperty(name = "quarkus.hazelcast-client.cluster-members") String ips) {
        ClientConfig clientConfig = new ClientConfig();
        Config config = new XmlConfigBuilder().build();
        // config.setClassLoader()
        // clientConfig.setClassLoader(Thread.currentThread().getContextClassLoader());
        // clientConfig.getNetworkConfig().setSmartRouting(false);
        // clientConfig.getNetworkConfig().setKubernetesConfig(new KubernetesConfig().setEnabled(true));
        // clientConfig.setSerializationConfig(new SerializationConfig().)
        String[] members = ips.split(",");
        clientConfig.getNetworkConfig().addAddress(members);
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(clientConfig);
        return hz;
    }

    void onStop(@Observes ShutdownEvent ev) {
        HazelcastInstance instance = Arc.container().instance(HazelcastInstance.class).get();
        if(instance != null) {
            instance.shutdown();
        }
    }

    public static void main(String[] args) {
        HazelcastInstance instance = hazelCaseClient("127.0.0.1");
        instance.getMap("123").put("hello", "world");
        System.out.println(instance.getMap("123").get("hello"));
        instance.shutdown();
    }

}
