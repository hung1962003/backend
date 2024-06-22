package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.RequestCompanyRequest;
import store.auroraauction.be.Models.AddPreliminaryValuation;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Process;
import store.auroraauction.be.entity.RequestBuy;
import store.auroraauction.be.entity.RequestCompany;
import store.auroraauction.be.repository.ProcessRepository;
import store.auroraauction.be.repository.RequestCompanyRepository;
import store.auroraauction.be.repository.RequestBuyRepository;
import store.auroraauction.be.utils.AccountUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RequestCompanyService {
    @Autowired
    private RequestCompanyRepository requestCompanyRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private RequestBuyRepository requestBuyRepository;
    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    EmailService emailService;
    public RequestCompany add(long requestUser_id) {// gan lien ket voi ng dung
        Account account = accountUtils.getCurrentAccount();
        Process process= processRepository.findProcessById(1);
        RequestBuy requestBuy = requestBuyRepository.findRequestBuyById(requestUser_id);
        RequestCompany requestCompany  = new RequestCompany();
           requestCompany.setRequestBuy(requestBuy);
        requestCompany.setStaff(account);
        requestCompany.setProcess(process);
        requestCompany.setSendtoCompany_status(false);
        requestCompanyRepository.save(requestCompany);
        return requestCompany;
    }
    public RequestCompany add1(RequestCompanyRequest requestCompany1) {
        Account account = accountUtils.getCurrentAccount();
        Process process= processRepository.findProcessById(1);
        RequestBuy requestBuy = requestBuyRepository.findRequestBuyById(requestCompany1.getRequestUser_id());
        RequestCompany requestCompany  = new RequestCompany();
        requestCompany.setRequestBuy(requestBuy);
        requestCompany.setStaff(account);
        requestCompany.setProcess(process);
        requestCompany.setSendtoCompany_status(false);
        requestCompanyRepository.save(requestCompany);
        return requestCompany;

    }


    public RequestCompany addpreliminaryvaluation(AddPreliminaryValuation addPreliminaryValuation,long id) { //dinh gia so bo
        Process process= processRepository.findProcessById(2);
        RequestCompany requestCompany  = requestCompanyRepository.findById(id).get();
        requestCompany.setEstimate_price_Max(addPreliminaryValuation.getEstimate_price_Max());
        requestCompany.setEstimate_price_Min(addPreliminaryValuation.getEstimate_price_Min());
        requestCompany.setProcess(process);
        requestCompanyRepository.save(requestCompany);
        return requestCompany;
    }
    public String deleterequestCompany(long id) {
        requestCompanyRepository.deleteById(id) ;
        return "requestCompany delteted";
    }

    public List<RequestCompany> getAllrequestCompany(){
        List<RequestCompany> requestCompany = requestCompanyRepository.findAll();
        return requestCompany;
    }

    public RequestCompany PreliminaryValuationApprovalAndSendToCompanyAndReceivedJewelry(Long id){
        RequestCompany requestCompany  = requestCompanyRepository.findRequestCompanyById(id);
        requestCompany.setSendtoCompany_status(true);
        Process process= processRepository.findProcessById(4);
        //RequestBuy requestBuy = requestBuyRepository.findRequestBuyById(requestCompany.getRequestBuy().getId());
        //gui email
        requestCompanyRepository.save(requestCompany);
        return requestCompany;
    }

    public RequestCompany SendToManagerAndFinalValuation(Long id){
        Account account = accountUtils.getCurrentAccount();
        Process process= processRepository.findProcessById(6);
        RequestCompany requestCompany  = requestCompanyRepository.findRequestCompanyById(id);
        requestCompany.setManager(account);
        requestCompany.setProcess(process);
        return null;
    }
    public List<RequestCompany> getRequestCompanyWithEstimatePrices() {
        List<RequestCompany> items =requestCompanyRepository.findAll();
        return items.stream()
                .filter(item -> item.getEstimate_price_Max() >0  && item.getEstimate_price_Min() >0)
                .collect(Collectors.toList());
    }
    public void threadSendMail(Account user,String subject, String description){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMail(user,subject,description);
            }

        };
        new Thread(r).start();
    }

    public RequestCompany getrequestCompany(long id) {
        RequestCompany requestCompany = requestCompanyRepository.findById(id).get();
        return requestCompany;
    }
//    public RequestCompany updaterequestCompany(long id, RequestCompany newrequestCompany) {
//        RequestCompany requestCompany = requestCompanyRepository.findById(id).get();
//        requestCompany.setDescription(newrequestCompany.getDescription());
//        requestCompany.setTitle(newrequestCompany.getTitle());
//        requestCompany.setImage(newrequestCompany.getImage_url());
//        requestCompanyRepository.save(requestCompany);
//        return requestCompany;
//    }
}
