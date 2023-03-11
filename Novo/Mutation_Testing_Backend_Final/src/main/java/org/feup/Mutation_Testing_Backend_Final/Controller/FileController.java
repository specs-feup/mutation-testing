package org.feup.Mutation_Testing_Backend_Final.Controller;

import org.feup.Mutation_Testing_Backend_Final.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/getAll")
    public void getAll(){
        return;
    }
}
