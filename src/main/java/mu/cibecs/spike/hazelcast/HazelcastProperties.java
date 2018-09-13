package mu.cibecs.spike.hazelcast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HazelcastProperties {

    private DiscoveryMode discoveryMode = DiscoveryMode.DEFAULT;
    private String discoveryKubernetesNamespace = "jx";
    private String discoveryKubernetesServiceName = "demo";

    public enum DiscoveryMode {
        DEFAULT, KUBERNETES
    }
}
