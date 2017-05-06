package ist.meic.cmu.controller;

import ist.meic.cmu.domain.Message;
import ist.meic.cmu.dto.MessageDto;
import ist.meic.cmu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by jp_s on 4/10/2017.
 */
@RestController
@RequestMapping(value = "message")
public class MessageControler extends LocmessController {

    @Autowired
    MessageService messageService;

    @RequestMapping(method = RequestMethod.POST, value = "/post")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postMessage(HttpServletRequest request, @RequestBody Message message){
        messageService.postMessage(request.getHeader(TOKEN_HEADER),message);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/unpost")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unpostMessage(HttpServletRequest request, @RequestBody MessageDto message) throws ServletException {
        messageService.unpostMessage(request.getHeader(TOKEN_HEADER),message);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public List<MessageDto> list(HttpServletRequest request){
        return messageService.listMessages(request.getHeader(TOKEN_HEADER));
    }

}
