package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.Models.MemberToTalResponseDTO;
import store.auroraauction.be.Models.ProfitResponseDTO;
import store.auroraauction.be.service.AdminService;

import java.util.List;

@RestController // nhan dien api
@CrossOrigin("*")
@RequestMapping("/api/admin")
@SecurityRequirement(name = "api")
public class AdminAPI {
    @Autowired
    AdminService adminService;
    @GetMapping("/countUser")
    public ResponseEntity countUser(){
        return  ResponseEntity.ok( adminService.adminResponseCountUser());
    }


    @GetMapping("/ProfitByMonth")
    public ResponseEntity getProfitByMonth(){
        List<ProfitResponseDTO> revenuePortal = adminService.getProfitByMonth();
        return  ResponseEntity.ok( revenuePortal);
    }

    @GetMapping("/countJewelry")
    public ResponseEntity countJewelry(){
        return  ResponseEntity.ok( adminService.adminResponseCountJewelry());
    }

}
