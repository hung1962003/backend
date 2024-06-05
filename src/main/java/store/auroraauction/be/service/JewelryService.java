package store.auroraauction.be.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Jewelry;
import store.auroraauction.be.repository.JewelryRepository;

import java.util.List;

@Service
public class JewelryService {
    @Autowired
    JewelryRepository jewelryRepository;
    public Jewelry addJewelry(Jewelry newjewelry) {
         Jewelry jewelry = new Jewelry();
         jewelry.setJewelry_condition(newjewelry.isJewelry_condition());
        jewelry.setDescription(newjewelry.getDescription());
        //jewelry.setBuy_status(newjewelry.isBuy_status());
        //jewelry.setBid_id(newjewelry.getBid_id());
        jewelry.setImage(newjewelry.getImage());
        //jewelry.setCategory_id(newjewelry.getCategory_id());
        jewelry.setConditionReport(newjewelry.getConditionReport());
        jewelry.setLast_price(newjewelry.getLast_price());
        jewelry.setTitle(newjewelry.getTitle());
        //jewelry.setSell_id(newjewelry.getSell_id());
        jewelry.setHigh_estimated_price(newjewelry.getHigh_estimated_price());
        //jewelry.setSell_status(newjewelry.isSell_status());
        jewelry.setLow_estimated_price(newjewelry.getLow_estimated_price());
        //jewelry.setBuy_id(newjewelry.getBuy_id());
        jewelry.setConditionReport(newjewelry.getConditionReport());
        jewelryRepository.save(jewelry);
        return jewelry;
    }
    public Jewelry updateJewelry(Jewelry newjewelry, Long id) {
        Jewelry jewelry =  jewelryRepository.findById(id).get();
        jewelry.setJewelry_condition(newjewelry.isJewelry_condition());
        jewelry.setDescription(newjewelry.getDescription());
        //jewelry.setBuy_status(newjewelry.isBuy_status());
        //jewelry.setBid_id(newjewelry.getBid_id());
        jewelry.setImage(newjewelry.getImage());
        //jewelry.setCategory_id(newjewelry.getCategory_id());
        jewelry.setConditionReport(newjewelry.getConditionReport());
        jewelry.setLast_price(newjewelry.getLast_price());
        jewelry.setTitle(newjewelry.getTitle());
        //jewelry.setSell_id(newjewelry.getSell_id());
        jewelry.setHigh_estimated_price(newjewelry.getHigh_estimated_price());
        //jewelry.setSell_status(newjewelry.isSell_status());
        jewelry.setLow_estimated_price(newjewelry.getLow_estimated_price());
        //jewelry.setBuy_id(newjewelry.getBuy_id());
        jewelry.setConditionReport(newjewelry.getConditionReport());
        return jewelryRepository.save(jewelry);
    }
        public String deleteJewelry(Long id) {
        jewelryRepository.deleteById(id);
        return "Jewelry deleted";
    }
    public Jewelry getJewelry(Long id) {
        Jewelry jewelry= jewelryRepository.findById(id).get();
        return jewelry;
    }
    public List<Jewelry> getJewelrys() {
        List<Jewelry> jewelries= jewelryRepository.findAll();
        return jewelries;
    }
}
