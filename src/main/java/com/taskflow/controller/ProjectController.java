package com.taskflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.taskflow.model.Project;
import com.taskflow.service.ProjectService;

@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public Project createProject(@RequestBody Project project){
        return projectService.saveProject(project);
    }

    @GetMapping
    public List<Project> getProjects(){
        return projectService.getProjects();
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return "Project deleted successfully";
    }
}
