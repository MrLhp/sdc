package sdc.config.cache;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import sdc.config.Constants;
import sdc.config.database.DatabaseConfiguration;
import sdc.config.web.WebConfiguration;

import javax.annotation.PreDestroy;

/**
 * 基于Hazelcast的Hibernate二级缓存配置
 */
@Configuration
@EnableCaching
@AutoConfigureBefore(value = { WebConfiguration.class, DatabaseConfiguration.class })
public class CacheConfiguration  extends CachingConfigurerSupport {

    private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    private final Environment env;

    @Qualifier("hazelcastInstance")
    @Autowired
    HazelcastInstance hazelcastInstance;

    public CacheConfiguration(Environment env) {
        this.env = env;
    }

    @PreDestroy
    public void destroy() {
        log.info("Closing Cache Manager");
        Hazelcast.shutdownAll();
    }

    @Override
    @Bean
    public CacheManager cacheManager() {
        log.debug("Starting HazelcastCacheManager");
        CacheManager cacheManager = new HazelcastCacheManager(hazelcastInstance);
        return cacheManager;
    }

    @Bean(name = "hazelcastInstance")
    public HazelcastInstance hazelcastInstance(CacheProperties properties) {
        log.debug("Configuring Hazelcast");
        Config config = new Config();
        config.setInstanceName("webapp");
        config.getNetworkConfig().setPort(5701);
        config.getNetworkConfig().setPortAutoIncrement(true);

        // In development, remove multicast auto-configuration
        if (env.acceptsProfiles(Constants.SPRING_PROFILE_DEVELOPMENT)) {
            System.setProperty("hazelcast.local.localAddress", "127.0.0.1");

            config.getNetworkConfig().getJoin().getAwsConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);
        }
        config.getMapConfigs().put("default", initializeDefaultMapConfig());
        config.getMapConfigs().put("com.mycompany.myapp.domain.*", initializeDomainMapConfig(properties));
        return Hazelcast.newHazelcastInstance(config);
    }

    private MapConfig initializeDefaultMapConfig() {
        MapConfig mapConfig = new MapConfig();

    /*
        Number of backups. If 1 is set as the backup-count for example,
        then all entries of the map will be copied to another JVM for
        fail-safety. Valid numbers are 0 (no backup), 1, 2, 3.
     */
        mapConfig.setBackupCount(0);

    /*
        Valid values are:
        NONE (no eviction),
        LRU (Least Recently Used),
        LFU (Least Frequently Used).
        NONE is the default.
     */
        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);

    /*
        Maximum size of the map. When max size is reached,
        map is evicted based on the policy defined.
        Any integer between 0 and Integer.MAX_VALUE. 0 means
        Integer.MAX_VALUE. Default is 0.
     */
        mapConfig.setMaxSizeConfig(new MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE));

        return mapConfig;
    }

    private MapConfig initializeDomainMapConfig(CacheProperties properties) {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(properties.getHazelcast().getTimeToLiveSeconds());
        mapConfig.setBackupCount(properties.getHazelcast().getBackupCount());
        return mapConfig;
    }
}
