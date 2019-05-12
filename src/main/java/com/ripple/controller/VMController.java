package com.ripple.controller;

import com.ripple.exception.VmNotFoundException;
import com.ripple.model.ApplicationCode;
import com.ripple.model.ApplicationResponse;
import com.ripple.model.GetVmResponse;
import com.ripple.model.VM;
import com.ripple.repository.VMRepository;
import com.ripple.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by intel on 5/10/2019.
 */
@RestController
@RequestMapping("/vms")
public class VMController {

    private VMRepository vmRepository;

    public VMController(VMRepository vmRepository) {
        this.vmRepository = vmRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<GetVmResponse> getVm( @RequestHeader("User_Token") String userToken){

        String userId = null;
        if(userToken == null || userToken.isEmpty())
        {
            GetVmResponse rep = new GetVmResponse(ApplicationCode.INVALID_USER_TOKEN,"Invalid User token");
            return new ResponseEntity<GetVmResponse>(rep, HttpStatus.UNAUTHORIZED);
        }
        if((userId = TokenService.validateToken(userToken))!= null){

            GetVmResponse rep = new GetVmResponse(ApplicationCode.SUCCESS,"Vm Get Succcessfull");
            System.out.println("Fetching the vm for --> "+Long.parseLong(userId));
            List<VM> vmlist = vmRepository.findByUserId(userId);
            rep.setVmList(vmlist);
            return new ResponseEntity<GetVmResponse>( rep, HttpStatus.OK);
        }
        else
        {
            GetVmResponse rep = new GetVmResponse(ApplicationCode.USER_TOKEN_TEMPERED,"User Tokken Tempered");
            return new ResponseEntity<GetVmResponse>( rep, HttpStatus.UNAUTHORIZED);
        }
    }



    //TODO: Add the endpoint to post the VM for a user
    @PostMapping("/create")
    public ResponseEntity<ApplicationResponse> createVm(@RequestBody VM vm,
                                                        @RequestHeader("User_Token") String userToken){
        String userId = null;
        if(userToken == null || userToken.isEmpty())
        {
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.INVALID_USER_TOKEN,"Invalid User token");
            return new ResponseEntity<ApplicationResponse>( rep, HttpStatus.UNAUTHORIZED);
        }
        if((userId = TokenService.validateToken(userToken)) != null){
            vm.setUserId(userId);
            System.out.println("saving the VM ---- >" + vm);
            vmRepository.save(vm);
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.SUCCESS,"VM created succesfully");
            return new ResponseEntity<ApplicationResponse>( rep, HttpStatus.OK);
        }
        else
        {
            //TODO:: validate the VM , also think about putting the userId inside the Token itself
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.USER_TOKEN_TEMPERED,"User Tokken Tempered");
            return new ResponseEntity<ApplicationResponse>(rep, HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/update/{vmId}")
    public ResponseEntity<ApplicationResponse> updateVm(@RequestBody VM vm, @PathVariable("vmId") String vmId,
                                                        @RequestHeader("User_Token") String userToken){

        if(userToken == null || userToken.isEmpty())
        {
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.INVALID_USER_TOKEN,"Invalid User token");
            return new ResponseEntity<ApplicationResponse>( rep, HttpStatus.UNAUTHORIZED);
        }
        if(TokenService.validateToken(userToken) != null){
            Optional<VM> existingVmOpt = vmRepository.findById(Long.parseLong(vmId));
            if(!existingVmOpt.isPresent())
            {
                throw new VmNotFoundException("No VM found for vmId "+vmId);
            }
            VM existingVM = existingVmOpt.get();
            updateVM(existingVM, vm);
            System.out.println("Saving updated VM ---> "+ existingVM);
            vmRepository.save(existingVM);
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.SUCCESS,"VM updated succesfully");
            return new ResponseEntity<ApplicationResponse>( rep, HttpStatus.OK);
        }
        else
        {
            //TODO:: validate the VM , also think about putting the userId inside the Token itself
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.USER_TOKEN_TEMPERED,"User Tokken Tempered");
            return new ResponseEntity<ApplicationResponse>( rep, HttpStatus.UNAUTHORIZED);
        }
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
