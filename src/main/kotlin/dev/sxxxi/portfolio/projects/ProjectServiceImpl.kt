package dev.sxxxi.portfolio.projects

import dev.sxxxi.portfolio.projects.exception.ProjectConflictException
import dev.sxxxi.portfolio.projects.exception.ProjectNotFoundException
import dev.sxxxi.portfolio.projects.domain.Project
import org.springframework.stereotype.Service

@Service
class ProjectServiceImpl(
    private val projectRepository: ProjectRepository
) : ProjectService {
    override fun create(instance: Project): Project {
        // Make sure all instances are unique
        instance.id?.let { id ->
            if (projectRepository.existsById(id)) {
                throw ProjectConflictException("Provided instance is not unique.")
            }
        }
        return projectRepository.save(instance)
    }

    override fun update(instance: Project): Project {
        instance.id?.let { id ->
            if (projectRepository.existsById(id)) {
                return projectRepository.save(instance)
            }
        }
        throw ProjectNotFoundException("Cannot update non-existing project.")
    }

    override fun getAll(): List<Project> {
        return projectRepository.findAll().toList()
    }

    override fun getById(id: Long): Project {
        return projectRepository.findById(id).orElseThrow {
            ProjectNotFoundException("Project with provided ID not found.")
        }
    }

    override fun delete(id: Long): Long {
        // Provide a way to inform API consumers that the item they are trying to delete does not exist in the first place
        if (!projectRepository.existsById(id)) {
            throw ProjectNotFoundException("Unable to delete non-existing project.")
        }
        projectRepository.deleteById(id)
        return id
    }
}