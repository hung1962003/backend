package store.auroraauction.be.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.auroraauction.be.entity.Process;
import store.auroraauction.be.repository.ProcessRepository;

import java.util.List;

@Service
public class ProcessService {
    @Autowired
    private ProcessRepository processRepository;


    public Process add( Process newprocess ) {
        Process process = new Process();
        processRepository.save(process);
        return process;
    }
    public String deleteProcess(int id) {
        processRepository.deleteById( id) ;
        return "process delteted";
    }

    public List<Process> getAllProcess(){
        List<Process> process = processRepository.findAll();
        return process;
    }
    public Process getProcess(int id) {
        Process process = processRepository.findById(id).get();
        return process;
    }
    public Process updateProcess(int id, Process newprocess) {
        Process process = processRepository.findById(id).get();


        processRepository.save(process);
        return process;
    }
}
