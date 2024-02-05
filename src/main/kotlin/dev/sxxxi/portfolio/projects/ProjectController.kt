package dev.sxxxi.portfolio.projects

import dev.sxxxi.portfolio.media.ContentStoreService
import dev.sxxxi.portfolio.media.domain.Services
import dev.sxxxi.portfolio.projects.domain.request.ProjectDto
import dev.sxxxi.portfolio.projects.domain.response.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    value = ["/portfolio/projects"],
    produces = ["application/json"],
)
@CrossOrigin(originPatterns = ["*"])
class ProjectController(
    private val projectService: ProjectService,
    private val contentService: ContentStoreService
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(value = ["/", ""])
    fun getAll(): ProjectListResponse {
        return ProjectListResponse(
            projects = projectService.getAll()
        )
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): ProjectGetResponse {
        return ProjectGetResponse(
            projectService.getById(id)
        )
    }

    @PostMapping(value = ["/", ""])
    fun create(
        @ModelAttribute project: ProjectDto,
    ): ProjectCreateResponse {
        val imagePaths = project.images.map { file ->
            contentService.store(Services.PROJECTS, file)
        }
        val entity = ProjectEntity(
            title = project.title,
            description = project.description,
            repoLink = project.repoLink,
            deployLink = project.deployedLink,
            imagePaths = imagePaths
        )

        return ProjectCreateResponse(
            project = projectService.create(entity)
        )
    }

    @PutMapping(value = ["/", ""])
    fun update(
        @RequestBody project: ProjectEntity
    ): ProjectUpdateResponse {
        return ProjectUpdateResponse(
            updated = projectService.update(project)
        )
    }

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long
    ): ProjectDeleteResponse {
        return ProjectDeleteResponse(
            projectService.deleteById(id)
        )
    }
}