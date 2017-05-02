package ist.meic.cmu.controller;

import ist.meic.cmu.domain.Location;
import ist.meic.cmu.domain.Pair;
import ist.meic.cmu.service.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Diogo on 01/05/2017.
 */
@RestController
@RequestMapping(value = "misc")
public class MiscController extends LocmessController {

    @Autowired
    MiscService miscService;

    @RequestMapping(method = RequestMethod.GET, value = "/listPairs")
    public List<Pair> listAllProfilesPairs(HttpServletRequest httpServletRequest) {
        return miscService.listAllProfilesPairs(httpServletRequest.getHeader(TOKEN_HEADER));
    }

}
