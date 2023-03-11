package com.feup.mutationTestingBackend.controller;

import com.feup.mutationTestingBackend.wrapper.FilePathWrapper;
import com.feup.mutationTestingBackend.wrapper.KadabraWrapper;
import com.feup.mutationTestingBackend.service.KadabraService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class KadabraController {
    private final KadabraService kadabraService;
    static Logger logger = Logger.getLogger(KadabraController.class.getName());

    @Autowired
    public KadabraController(KadabraService kadabraService) {
        this.kadabraService = kadabraService;
    }

    @PostMapping("/executeKadabra")
    public String executeKadabra(@RequestBody KadabraWrapper kw){
        logger.info("Request to call Kadabra");

        kadabraService.executeKadabra(kw);

        return "Calling Kadabra";
    }

    @PostMapping("/getFiles")
    public ArrayList<String> getFileList(@RequestBody FilePathWrapper filePathWrapper){
        System.out.println(filePathWrapper);
        return kadabraService.getFileList(filePathWrapper.getPath());
    }

    @PostMapping("/teste2")
    public ArrayList<String> executeKadabra2(@RequestBody String filePathWrapper){

        return kadabraService.getFileList(filePathWrapper);
    }

    @GetMapping("/teste")
    public  FilePathWrapper getKadabraArgs(){
        FilePathWrapper filePathWrapper = new FilePathWrapper("/path");
        return filePathWrapper;
    }


    @GetMapping("/getAll")
    public  Map<String, List<String>> teste(){
        String pathToProject = "C:\\Users\\david\\Desktop\\TestProject\\src\\test";
        Map<String, List<String>> testClassesAndCases = kadabraService.listTestClassesAndCases(pathToProject);
        System.out.println(testClassesAndCases);
        return testClassesAndCases;
    }

}
