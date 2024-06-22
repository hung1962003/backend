package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.RequestBuyRequest;
import store.auroraauction.be.entity.RequestBuy;

import store.auroraauction.be.enums.RequestBuyEnum;
import store.auroraauction.be.repository.RequestBuyRepository;
import store.auroraauction.be.utils.AccountUtils;

import java.util.List;
@Service
public class RequestBuyServices {
    @Autowired
    private RequestBuyRepository requestBuyRepository;
    @Autowired
    private AccountUtils accountUtils;
    public RequestBuy add(RequestBuyRequest newrequest ) {
        RequestBuy request = new RequestBuy();
        request.setDescription(newrequest.getDescription());
        request.setTitle(newrequest.getTitle());
        request.setRequestBuyEnum(RequestBuyEnum.PENDING);
        request.setAccount(accountUtils.getCurrentAccount());
        request.setImage(newrequest.getImage_url());
        requestBuyRepository.save(request);
        return request;
    }
    public String deleteRequest(long id) {
        requestBuyRepository.deleteById(id) ;
        return "request delteted";
    }

    public List<RequestBuy> getAllRequest(){
        List<RequestBuy> request = requestBuyRepository.findAll();
        return request;
    }
    public RequestBuy getRequest(long id) {
        RequestBuy request = requestBuyRepository.findById(id).get();
        return request;
    }
    public RequestBuy updateRequest(long id, RequestBuyRequest newrequest) {
        RequestBuy request = requestBuyRepository.findById(id).get();
        request.setDescription(newrequest.getDescription());
        request.setTitle(newrequest.getTitle());
        request.setImage(newrequest.getImage_url());
        requestBuyRepository.save(request);
        return request;
    }
}
