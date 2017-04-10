package ist.meic.cmu.controller;

import ist.meic.cmu.domain.Message;
import ist.meic.cmu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jp_s on 4/10/2017.
 */
@RestController
@RequestMapping(value = "Message")
public class MessageControler {

    @Autowired
    MessageService messageService;

    @RequestMapping(method = RequestMethod.POST, value = "/post")
    public void postMessage(@RequestBody Message message){
        messageService.postMessage(message);
    }

}
