package com.ripple.controller;

import com.ripple.exception.InvalidUserTokenException;
import com.ripple.exception.VmNotFoundException;
import com.ripple.model.ApplicationCode;
import com.ripple.model.ApplicationResponse;
import com.ripple.model.GetVmResponse;
import com.ripple.model.VM;
import com.ripple.repository.VMRepository;
import com.ripple.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by intel on 5/10/2019.
 */
@RestController
@RequestMapping("/vms")
public class VMController {

    @Autowired
    VMRepository vmRepository;

    @Autowired
    TokenService tokenService;
    Logger logger = Logger.getLogger(VMController.class.getName());

    @GetMapping("/list")
    public ResponseEntity<GetVmResponse> getVm( @RequestHeader("User_Token") String userToken){

        ResponseEntity<GetVmResponse> responseEntity = null;
        String userId = null;
        if(userToken == null || userToken.isEmpty())
        {

            logger.warning("User Token Empty or invalid");
            throw new InvalidUserTokenException("Invalid User Token");
        }
        if((userId = tokenService.validateToken(userToken))!= null){

            GetVmResponse rep = new GetVmResponse(ApplicationCode.SUCCESS,"Vm Get Succcessfull");
            logger.info("Fetching the vm for --> "+Long.parseLong(userId));
            List<VM> vmlist = vmRepository.findByUserId(userId);
            rep.setVmList(vmlist);
            responseEntity =  new ResponseEntity<GetVmResponse>( rep, HttpStatus.OK);
        }
   return responseEntity;
    }

    @PostMapping("/create")
    public ResponseEntity<ApplicationResponse> createVm(@RequestBody VM vm,
                                                        @RequestHeader("User_Token") String userToken){
        String userId = null;
        ResponseEntity<ApplicationResponse> responseEntity = null;
        if(userToken == null || userToken.isEmpty())
        {
            logger.warning("User token empty or invalid");
            throw new InvalidUserTokenException("Invalid User Token");
        }

        if((userId = tokenService.validateToken(userToken)) != null){
            vm.setUserId(userId);
            logger.info("saving the VM ---- >" + vm);
            vmRepository.save(vm);
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.SUCCESS,"VM created succesfully");
            responseEntity = new ResponseEntity<ApplicationResponse>( rep, HttpStatus.OK);
        }
        return responseEntity;
    }


    @PostMapping("/update/{vmId}")
    public ResponseEntity<ApplicationResponse> updateVm(@RequestBody VM vm, @PathVariable("vmId") String vmId,
                                                        @RequestHeader("User_Token") String userToken){

        ResponseEntity<ApplicationResponse> responseEntity = null;
        if(userToken == null || userToken.isEmpty())
        {
            logger.warning("User tokken empty or invalid");
            throw new InvalidUserTokenException("Invalid User Token");

        }
        if(tokenService.validateToken(userToken) != null){
            Optional<VM> existingVmOpt = vmRepository.findById(Long.parseLong(vmId));
            if(!existingVmOpt.isPresent())
            {
                logger.warning("No VM info found for vm Id " + vmId);
                throw new VmNotFoundException("No VM found for vmId "+vmId);
            }
            VM existingVM = existingVmOpt.get();
            updateVM(existingVM, vm);
            logger.info("Saving updated VM ---> "+ existingVM);
            vmRepository.save(existingVM);
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.SUCCESS,"VM updated succesfully");
            responseEntity = new ResponseEntity<ApplicationResponse>( rep, HttpStatus.OK);
        }
        return responseEntity;

    }

    private void updateVM(VM existingVM, VM vm) {
        if(vm.getOs()!=null)
            existingVM.setOs(vm.getOs());
        if(vm.getRam()!=null)
            existingVM.setRam(vm.getRam());
        if(vm.getHardDisk()!=null)
            existingVM.setHardDisk(vm.getHardDisk());
        if(vm.getCpucores()!=null)
            existingVM.setCpucores(vm.getCpucores());

    }


}
