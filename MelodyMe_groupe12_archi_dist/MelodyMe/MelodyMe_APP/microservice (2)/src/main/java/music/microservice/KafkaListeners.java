package music.microservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(
            topics = "musiques",groupId = "MusicGrp")
    void listener(String data){
        System.out.println("Listener recieved "+data);
    }
}
