package dev.sxxxi.portfolio.projects

import dev.sxxxi.portfolio.media.ContentStoreService
import dev.sxxxi.portfolio.projects.domain.Project
import dev.sxxxi.portfolio.projects.exception.ProjectConflictException
import dev.sxxxi.portfolio.projects.exception.ProjectNotFoundException
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import kotlin.coroutines.coroutineContext

@Service
class ProjectServiceImpl(
    private val projectRepository: ProjectRepository,
    private val contentService: ContentStoreService
) : ProjectService {

    override fun create(instance: ProjectEntity): Project {
        instance.id?.let { id ->
            if (projectRepository.existsById(id)) {
                throw ProjectConflictException("Provided instance is not unique.")
            }
        }

        return toDomain(projectRepository.save(instance))
    }

    override fun update(instance: ProjectEntity): Project {
        instance.id?.let { id ->
            if (projectRepository.existsById(id)) {
                return toDomain(projectRepository.save(instance))
            }
        }
        throw ProjectNotFoundException("Cannot update non-existing project.")
    }

    override fun getAll(): List<Project> {
        return projectRepository.findAll().toList().map {
            return@map Project(
                title = it.title,
                description = it.description,
                repoLink = it.repoLink,
                deployedLink = it.deployLink,
                imageLinks = it.imagePaths.map { p ->  contentService.get(p) }
            )
        }
    }

    override fun getById(id: Long): Project {
        val entity = projectRepository.findById(id).orElseThrow {
            ProjectNotFoundException("Project with provided ID not found.")
        }

        return toDomain(entity)
    }

    override fun deleteById(id: Long): Long {
        // Provide a way to inform API consumers that the item they are trying to delete does not exist in the first place
        val project = projectRepository.findById(id)

        project.ifPresent { p ->
            // Delete images in the bucket first
            p.imagePaths.forEach {
                contentService.delete(it)
            }
            projectRepository.deleteById(id)
        }
        project.orElseThrow {
            throw ProjectNotFoundException("Unable to delete non-existing project.")
        }

        return id
    }

    private fun toDomain(entity: ProjectEntity): Project {
        return Project(
            title = entity.title,
            description = entity.description,
            repoLink = entity.repoLink,
            deployedLink = entity.deployLink,
            imageLinks = entity.imagePaths.map { p ->  contentService.get(p) }
        )
    }
}