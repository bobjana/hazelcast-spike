package mu.cibecs.spike.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
public class HazelcastApplication {

	public static void main(String[] args) {
		SpringApplication.run(HazelcastApplication.class, args);
	}

	@RestController
	public static class TestController{

		@Autowired
		private HazelcastInstance hazelcastInstance;

		@RequestMapping(value = "/test",method = RequestMethod.GET)
		public void test(){
			ITopic topic = hazelcastInstance.getTopic("app" );
			topic.publish(new AppEvent(UUID.randomUUID()));
		}
	}
}
