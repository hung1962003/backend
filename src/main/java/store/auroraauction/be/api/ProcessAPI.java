package store.auroraauction.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.auroraauction.be.entity.Process;
import store.auroraauction.be.service.ProcessService;

@RestController
@RequestMapping("/api/process")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class ProcessAPI {
    @Autowired
    private ProcessService processService;
    @PostMapping()
    public ResponseEntity add(@RequestBody Process newprocess){
        Process process =processService.add(newprocess);
        return ResponseEntity.ok(process);
    }
    @GetMapping()
    public ResponseEntity getAllProcess(){
        return ResponseEntity.ok(processService.getAllProcess());
    }
    @GetMapping("{id}")
    public ResponseEntity getProcess(@PathVariable int id){
        return ResponseEntity.ok(processService.getProcess(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteProcess(@PathVariable int id){

        return ResponseEntity.ok(processService.deleteProcess(id));
    }
    @PutMapping("{id}")
    public ResponseEntity updateProcess(@PathVariable int id,@RequestBody Process newprocess){

        return ResponseEntity.ok( processService.updateProcess(id,newprocess));
    }
}
