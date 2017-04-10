package ist.meic.cmu.service;

import ist.meic.cmu.domain.Message;
import ist.meic.cmu.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jp_s on 4/10/2017.
 */
@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public void postMessage(Message message){
        messageRepository.saveAndFlush(message);
    }


}
