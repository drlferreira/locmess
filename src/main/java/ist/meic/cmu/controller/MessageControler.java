package ist.meic.cmu.controller;

import ist.meic.cmu.domain.Message;
import ist.meic.cmu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jp_s on 4/10/2017.
 */
@RestController
@RequestMapping(value = "message")
public class MessageControler {

    @Autowired
    MessageService messageService;

    @RequestMapping(method = RequestMethod.POST, value = "/post")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postMessage(@RequestBody Message message){
        messageService.postMessage(message);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/unpost")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unpostMessage(@RequestBody Message message){
        messageService.unpostMessage(message);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void list(){
        messageService.listMessages();
    }

}
