package MelodyMe.profiles;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(
            topics = "profiles",groupId = "UserGrp")
    void listener(String data){
        System.out.println("Listener recieved "+data);
    }
}
