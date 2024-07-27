package store.auroraauction.be.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.JewelryRequest;
import store.auroraauction.be.Models.PrelimaryRequest;
import store.auroraauction.be.Models.RequestBuyRequest;
import store.auroraauction.be.Models.SendToManagerRequest;
import store.auroraauction.be.entity.*;

import store.auroraauction.be.entity.Process;
import store.auroraauction.be.enums.RequestBuyEnum;
import store.auroraauction.be.enums.StatusJewelryEnum;
import store.auroraauction.be.repository.*;
import store.auroraauction.be.utils.AccountUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestBuyServices {
    @Autowired
    private RequestBuyRepository requestBuyRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private EmailService emailService;
    @Autowired
    JewelryService jewelryService;


    @Autowired
    AutheticationRepository autheticationRepository;

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    JewelryRepository jewelryRepository;

    @Autowired
    CategoryRepository categoryRepository;
    public RequestBuy add(RequestBuyRequest newrequest) { // tao request ban
        RequestBuy request = new RequestBuy();
        Category category = categoryRepository.findById(newrequest.getCategory_id()).get();

        request.setDescription(newrequest.getDescription());
        request.setTitle(newrequest.getTitle());
        request.setRequestBuyEnum(RequestBuyEnum.PENDING);
        request.setImage(newrequest.getImage_url());
        request.setCategory_id(category.getId());
        //request.setWeight(newrequest.getWeight());
        // create process
        Process process = new Process();
        process.setRequestBuyEnum(RequestBuyEnum.PENDING);
        List<Process> processes = new ArrayList<Process>();
        processes.add(process);
        request.setProcesses(processes);


        request.setAccount(accountUtils.getCurrentAccount());
        request.setImage(newrequest.getImage_url());


        request = requestBuyRepository.save(request);
        process.setRequestBuy(request);
        processRepository.save(process);


        return request;
    }

    public RequestBuy sendPrelimaryRequest(long id,PrelimaryRequest prelimaryRequest) {
        RequestBuy requestBuy = requestBuyRepository.findById(id);

        requestBuy.setMinPrice(prelimaryRequest.getMinPrice());
        requestBuy.setMaxPrice(prelimaryRequest.getMaxPrice());

        Process process = new Process();
        process.setMin(prelimaryRequest.getMinPrice());
        process.setMax(prelimaryRequest.getMaxPrice());
        process.setRequestBuyEnum(RequestBuyEnum.PRELIMARY);
        process.setStaffID(accountUtils.getCurrentAccount().getId());
        process.setRequestBuy(requestBuy);
        List<Process> processes = processRepository.findByRequestBuy_Id(requestBuy.getId());
        processes.add(process);
        processRepository.save(process);
        requestBuy.setProcesses(processes);
        return requestBuyRepository.save(requestBuy);

    }

    public String deleteRequest(long id) {
        requestBuyRepository.deleteById(id);
        return "request delteted";
    }

    public List<RequestBuy> getAllRequest() {
        List<RequestBuy> request = requestBuyRepository.findAll();
        return request;
    }

    public RequestBuy getRequest(long id) {
        RequestBuy request = requestBuyRepository.findById(id);
        return request;
    }

    public RequestBuy updateRequest(long id, RequestBuyRequest newrequest) {
        RequestBuy request = requestBuyRepository.findById(id);
        request.setDescription(newrequest.getDescription());
        request.setTitle(newrequest.getTitle());
        request.setImage(newrequest.getImage_url());
        requestBuyRepository.save(request);
        return request;
    }

    public void threadSendMail(Account user, String subject, String description) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                emailService.sendMail(user, subject, description);
            }

        };
        new Thread(r).start();
    }

    public RequestBuy acpPrelimary(long id) {
        RequestBuy requestBuy = requestBuyRepository.findById(id);


        Process process = new Process();
        process.setRequestBuyEnum(RequestBuyEnum.ACPBYUSER);
        process.setRequestBuy(requestBuy);



        List<Process> processes = processRepository.findByRequestBuy_Id(id);
        processes.add(process);

        processRepository.save(process);
        requestBuy.setProcesses(processes);
        return requestBuyRepository.save(requestBuy);
    }

    public RequestBuy sendToManager(long id, JewelryRequest JewelryRequest) {
        RequestBuy requestBuy = requestBuyRepository.findById(id);
        Process process = new Process();
        process.setRequestBuyEnum(RequestBuyEnum.WAITINGMANAGER);
        process.setRequestBuy(requestBuy);
        process.setManagerID(accountUtils.getCurrentAccount().getId());

        Jewelry jewelry = jewelryService.addJewelry(JewelryRequest);
        jewelry.setAccount(requestBuy.getAccount());
        jewelryRepository.save(jewelry);

        process.setJewelry(jewelry);
        requestBuy.setJewelry(jewelry);

        List<Process> processes = processRepository.findByRequestBuy_Id(requestBuy.getId());
        processes.add(process);

        processRepository.save(process);
        requestBuy.setProcesses(processes);
        return requestBuyRepository.save(requestBuy);
    }

    public RequestBuy FinalValuation(long id){
        RequestBuy requestBuy = requestBuyRepository.findById(id);

        Process process = new Process();

        process.setRequestBuyEnum(RequestBuyEnum.FINALVALUATION);
        process.setRequestBuy(requestBuy);
        process.setManagerID(accountUtils.getCurrentAccount().getId());

        requestBuy.getProcesses().add(process);
        return requestBuyRepository.save(requestBuy);

    }

    public RequestBuy sendBackClient(long id){
        RequestBuy requestBuy = requestBuyRepository.findById(id);
        Process process = new Process();
        process.setRequestBuyEnum(RequestBuyEnum.SENDBACK);
        process.setRequestBuy(requestBuy);
        process.setStaffID(accountUtils.getCurrentAccount().getId());
        requestBuy.getProcesses().add(process);
        return requestBuyRepository.save(requestBuy);
    }

    public RequestBuy AcceptToAuction(long id){
        RequestBuy requestBuy = requestBuyRepository.findById(id);
        Jewelry jewelry = requestBuy.getJewelry();
        jewelry.setStatusJewelryEnum(StatusJewelryEnum. Ready);
        jewelryRepository.save(jewelry);
        requestBuy.setRequestBuyEnum(RequestBuyEnum.COMPLETED);
        Process process = new Process();
        process.setRequestBuyEnum(RequestBuyEnum.COMPLETED);
        process.setRequestBuy(requestBuy);

        requestBuy.getProcesses().add(process);
        return requestBuyRepository.save(requestBuy);
    }
    public List<RequestBuy> getRequestByAccountID(){
        Account account = accountUtils.getCurrentAccount();
        List<RequestBuy> requestBuyList = requestBuyRepository.findRequestBuyByAccountId(account.getId());
        return requestBuyList;

    }
 }
