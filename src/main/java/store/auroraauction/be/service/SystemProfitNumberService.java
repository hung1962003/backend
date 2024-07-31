package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.SystemProfitNumberRequest;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.entity.SystemProfitNumber;
import store.auroraauction.be.repository.SystemProfitNumberRepository;
@Service
public class SystemProfitNumberService {

    @Autowired
    private SystemProfitNumberRepository systemProfitNumberRepository;
    public SystemProfitNumber updateSystemProfitNumber( SystemProfitNumberRequest systemProfitNumberRequest) {
        SystemProfitNumber systemProfitNumber = systemProfitNumberRepository.findById(1L).get();
        systemProfitNumber.setPercentofsystemprofit(systemProfitNumberRequest.getPercentofsystemprofit()/100);
        systemProfitNumberRepository.save(systemProfitNumber);
        return systemProfitNumber;
    }

    public SystemProfitNumber addSystemProfitNumber(SystemProfitNumberRequest systemProfitNumberRequest) {
            SystemProfitNumber systemProfitNumber= new SystemProfitNumber();
            systemProfitNumber.setPercentofsystemprofit(systemProfitNumberRequest.getPercentofsystemprofit()/100);
            systemProfitNumberRepository.save(systemProfitNumber);
            return systemProfitNumber;
    }
    public SystemProfitNumber getSystemProfitNumber() {
        SystemProfitNumber systemProfitNumber=systemProfitNumberRepository.findById(1L).get();
        return systemProfitNumber;
    }
}
