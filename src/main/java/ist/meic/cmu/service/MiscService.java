package ist.meic.cmu.service;

import ist.meic.cmu.domain.Pair;
import ist.meic.cmu.domain.User;
import ist.meic.cmu.repository.MessageRepository;
import ist.meic.cmu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Diogo on 01/05/2017.
 */
@Service
public class MiscService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    MessageService messageService;

    @Autowired
    MessageRepository messageRepository;

    public List<Pair> listAllProfilesPairs(String token) {
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        List<User> allUsers = userRepository.findAll();
        // avoid repeated pairs
        Set<Pair> pairs = new HashSet<>();
        pairs.addAll(user.getPairs());
        for (User u : allUsers){
            pairs.addAll(u.getPairs());
        }
        return new ArrayList<>(pairs);
    }

    /*
    public void confirmMsg(String token, MessageDto messageDto) {
        User user = userRepository.findUserByUsername(tokenService.getUsername(token));
        List<MessageDto> notifications = messageService.findInterests(user.getUsername());
        for (MessageDto md : notifications){
            if(md.getId().equals(messageDto.getId())){
                user.getMessages().add(messageRepository.findOne(md.getId()));
                userRepository.saveAndFlush(user);
                messageService.removeNotification(user.getUsername(),md.getId());
            }
        }
    }*/
}
