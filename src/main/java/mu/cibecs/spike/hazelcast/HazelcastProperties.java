package mu.cibecs.spike.hazelcast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HazelcastProperties {

    private DiscoveryMode discoveryMode = DiscoveryMode.DEFAULT;
    private String discoveryKubernetesNamespace = "cibecs";
    private String discoveryKubernetesServiceName = "demo-service";

    public enum DiscoveryMode {
        DEFAULT, KUBERNETES
    }
}
