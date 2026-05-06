package com.taskflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskflow.model.Project;
import com.taskflow.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project getProjects(Long id) {
        return projectRepository.findById(id).orElseThrow();
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
