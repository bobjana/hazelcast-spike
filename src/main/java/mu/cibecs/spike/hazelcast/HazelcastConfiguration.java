package mu.cibecs.spike.hazelcast;

import com.hazelcast.config.*;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class HazelcastConfiguration {

    @Bean
    @Profile("!hazelcast")
    public Config config() {
        Config cfg = new Config();
        cfg.setInstanceName("test");
        NetworkConfig network = cfg.getNetworkConfig();

        JoinConfig join = network.getJoin();
        join.getTcpIpConfig().setEnabled(false);
        join.getAwsConfig().setEnabled(false);
        final MulticastConfig multicastConfig = join.getMulticastConfig();
        multicastConfig.setMulticastGroup("224.0.0.1");
        multicastConfig.setEnabled(true);


        TopicConfig topicConfig = new TopicConfig("app");
        topicConfig.addMessageListenerConfig(
            new ItemListenerConfig( "mu.cibecs.spike.hazelcast.AppEventListener", true )
        );

        cfg.addTopicConfig(topicConfig);

        return cfg;
    }


    @Bean
    @Profile("hazelcast")
    @ConfigurationProperties("hazelcast.cluster")
    public HazelcastProperties hazelcastProperties() {
        return new HazelcastProperties();
    }

    @Bean
    @Profile("hazelcast")
    public Config kubernetesConfig() {
        Config config = new Config();
        if (HazelcastProperties.DiscoveryMode.KUBERNETES.equals(hazelcastProperties().getDiscoveryMode())) {
            config.setProperty("hazelcast.discovery.enabled", "true");

            NetworkConfig networkConfig = config.getNetworkConfig();

            JoinConfig joinConfig = networkConfig.getJoin();
            joinConfig.getMulticastConfig().setEnabled(false);
            joinConfig.getTcpIpConfig().setEnabled(false);

            DiscoveryConfig discoveryConfig = joinConfig.getDiscoveryConfig();
            DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(new HazelcastKubernetesDiscoveryStrategyFactory());
            discoveryStrategyConfig.addProperty("namespace", hazelcastProperties().getDiscoveryKubernetesNamespace());
            discoveryStrategyConfig.addProperty("service-name", hazelcastProperties().getDiscoveryKubernetesServiceName());
            discoveryConfig.addDiscoveryStrategyConfig(discoveryStrategyConfig);

            log.info("Configuring hazelcast kubernetes discovery strategy for namespace: {}, service-name: {}",
                     hazelcastProperties().getDiscoveryKubernetesNamespace(),
                     hazelcastProperties().getDiscoveryKubernetesServiceName());
        }
        return config;
    }

}
