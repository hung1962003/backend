package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.auroraauction.be.Models.*;
import store.auroraauction.be.entity.SystemProfit;
import store.auroraauction.be.enums.RoleEnum;
import store.auroraauction.be.enums.StatusJewelryEnum;
import store.auroraauction.be.repository.AutheticationRepository;
import store.auroraauction.be.repository.JewelryRepository;
import store.auroraauction.be.repository.SystemProfitRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    AutheticationRepository autheticationRepository;

    @Autowired
    SystemProfitRepository systemProfitRepository;

    @Autowired
    JewelryRepository jewelryRepository;
    public MemberToTalResponseDTO countUser() {
        int BuyerCount = autheticationRepository.countByRole(RoleEnum.BUYER.toString());
        int SellerCount = autheticationRepository.countByRole(RoleEnum.SELLER.toString());
        int StaffCount = autheticationRepository.countByRole(RoleEnum.STAFF.toString());
        int ManagerCount = autheticationRepository.countByRole(RoleEnum.MANAGER.toString());
        int ClientCount = autheticationRepository.countByRole(RoleEnum.CLIENT.toString());
        int total = ClientCount;
        MemberToTalResponseDTO memberToTalResponseDTO = new MemberToTalResponseDTO();
        memberToTalResponseDTO.setBuyerQuantity(BuyerCount);
        memberToTalResponseDTO.setSellerQuantity(SellerCount);
        memberToTalResponseDTO.setStaffQuantity(StaffCount);
        memberToTalResponseDTO.setManagerQuantity(ManagerCount);
        memberToTalResponseDTO.setClientQuantity(ClientCount);
        memberToTalResponseDTO.setTotalMember(total);
        return memberToTalResponseDTO;
    }

    public List<ProfitResponseDTO> getProfitByMonth() {
        int i;
        List<ProfitResponseDTO> list = new ArrayList<>();
        float revenuePortal;
        LocalDateTime monthnow = LocalDateTime.now();
        int monthValue = monthnow.getMonthValue();
        int year = monthnow.getYear();
        int month1 = monthValue - 7;// check co phai 7 month trong nam ko
        int monthLack = 0;
        List<SystemProfit> systemProfits;

        if (month1 < 0) {
            monthLack = month1 * (-1);
            // nam truoc
            for (i = 12 - monthLack; i <= 12; i++) {
                int month = i;
                List<ListSystemProfitMapByDTO> listSystemProfitMapByDTOS = new ArrayList<>();
                try {
                    revenuePortal = systemProfitRepository.getProfitByMonth(month, year - 1);
                    systemProfits = systemProfitRepository.getAllHistorySystemProfit(month, year - 1);
                } catch (Exception e) {
                    revenuePortal = 0;
                    systemProfits = new ArrayList<>();
                }
                for (SystemProfit systemProfit : systemProfits) {
                    ListSystemProfitMapByDTO listSystemProfitMapByDTO = new ListSystemProfitMapByDTO();
                    listSystemProfitMapByDTO.setId(systemProfit.getId());
                    listSystemProfitMapByDTO.setDescription(systemProfit.getDescription());
                    listSystemProfitMapByDTO.setBalance(systemProfit.getBalance());
                    listSystemProfitMapByDTO.setDate(systemProfit.getDate());
                    if (systemProfit.getTransaction() != null) {
                        listSystemProfitMapByDTO.setTransaction(systemProfit.getTransaction());
                    }
                    listSystemProfitMapByDTOS.add(listSystemProfitMapByDTO);
                }
                ProfitResponseDTO responseDTO = new ProfitResponseDTO();
                responseDTO.setMonth(month);
                responseDTO.setRevenuePortal(revenuePortal);
                responseDTO.setSystemProfits(listSystemProfitMapByDTOS);
                list.add(responseDTO);
            }
        }
        // nam hien tai
        for (i = monthValue - 7 + monthLack+1; i <= monthValue; i++) {
            int month = i;
            List<ListSystemProfitMapByDTO> listSystemProfitMapByDTOS = new ArrayList<>();
            try {
                revenuePortal = systemProfitRepository.getProfitByMonth(month, year);
                systemProfits = systemProfitRepository.getAllHistorySystemProfit(month, year);
            } catch (Exception e) {
                revenuePortal = 0;
                systemProfits = new ArrayList<>();
            }
            for (SystemProfit systemProfit : systemProfits) {
                ListSystemProfitMapByDTO listSystemProfitMapByDTO = new ListSystemProfitMapByDTO();//tao o day de ko bi lap
                listSystemProfitMapByDTO.setId(systemProfit.getId());
                listSystemProfitMapByDTO.setDescription(systemProfit.getDescription());
                listSystemProfitMapByDTO.setBalance(systemProfit.getBalance());
                listSystemProfitMapByDTO.setDate(systemProfit.getDate());
                if (systemProfit.getTransaction() != null) {
                    listSystemProfitMapByDTO.setTransaction(systemProfit.getTransaction());
                }
                listSystemProfitMapByDTOS.add(listSystemProfitMapByDTO);
            }
            ProfitResponseDTO responseDTO = new ProfitResponseDTO();
            responseDTO.setMonth(month);
            responseDTO.setRevenuePortal(revenuePortal);

            responseDTO.setSystemProfits(listSystemProfitMapByDTOS);
            list.add(responseDTO);
        }

        return list;
    }


    public JewelryCountResponse countJewelry(){
        int jewelryIsSold = jewelryRepository.countJewelryIsSold(StatusJewelryEnum.isSold.toString());
        int allJewelry = jewelryRepository.countAllJewelries();
        JewelryCountResponse jewelryCountResponse = new JewelryCountResponse();
        jewelryCountResponse.setJewelryIsSold(jewelryIsSold);
        jewelryCountResponse.setJewelryNotSold(allJewelry-jewelryIsSold);
        return jewelryCountResponse;
    }

    public AdminResponseCountUser adminResponseCountUser(){
        AdminResponseCountUser adminResponseCountUser = new AdminResponseCountUser();
        adminResponseCountUser.setLabel("count user");
        adminResponseCountUser.setData(countUser());
        return adminResponseCountUser;
    }

    public AdminResponseCountJewelry adminResponseCountJewelry(){
        AdminResponseCountJewelry adminResponseCountJewelry = new AdminResponseCountJewelry();
        adminResponseCountJewelry.setLabel("count jewelry");
        adminResponseCountJewelry.setData(countJewelry());
        return adminResponseCountJewelry;
    }
}
