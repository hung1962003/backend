package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.AuctionRequest;
import store.auroraauction.be.entity.Auction;
import store.auroraauction.be.entity.Identify;
import store.auroraauction.be.repository.IdentifyRepository;
import store.auroraauction.be.utils.AccountUtils;

import java.util.List;

@Service
public class IdentifyService {
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private IdentifyRepository  identifyRepository;
    public Identify add(Identify newidentify) {
        Identify identify = new Identify();
        identify.setAccount(accountUtils.getCurrentAccount());
        identify.setBackID_image(newidentify.getBackID_image());
        identify.setFrontID_image(newidentify.getFrontID_image());
        identifyRepository.save(identify);
        return identify;
    }
    public String deleteIdentify(long id) {
        identifyRepository.deleteById(id) ;
        return "identify delteted";
    }

    public List<Identify> getAllIdentify(){
        List<Identify> identifies = identifyRepository.findAll();
        return identifies;
    }
    public Identify getIdentify(long id) {
        Identify identify = identifyRepository.findById(id).get();
        return identify;
    }
    public Identify updateIdentify(long id1, Identify newidentify) {
        Identify identify = identifyRepository.findById(id1).get();
        identify.setBackID_image(newidentify.getBackID_image());
        identify.setFrontID_image(newidentify.getFrontID_image());
        identifyRepository.save(identify);
        return identify;
    }
}
