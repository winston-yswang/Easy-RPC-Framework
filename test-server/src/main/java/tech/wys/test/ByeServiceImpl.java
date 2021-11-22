package tech.wys.test;

import tech.wys.rpc.annotation.Service;
import tech.wys.rpc.api.ByeService;


/**
 * @author ziyang
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}
