package dev.sxxxi.portfolio.projects

import dev.sxxxi.portfolio.projects.domain.Project
import dev.sxxxi.portfolio.projects.domain.request.ProjectCreateRequest
import dev.sxxxi.portfolio.projects.domain.response.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(
    value = ["/portfolio/projects"],
    produces = ["application/json"],
)
class ProjectController(
    private val service: ProjectService
) {

    @GetMapping(value = ["/", ""])
    fun getProjects(): ProjectListResponse {
        return ProjectListResponse(
            projects = service.getAll()
        )
    }

    @GetMapping("/{id}")
    fun getProjectById(
        @PathVariable id: Long
    ): ProjectGetResponse {
        return ProjectGetResponse(
            service.getById(id)
        )
    }

    @PostMapping(value = ["/", ""])
    fun createProject(
        @RequestBody project: Project
    ): ProjectCreateResponse {
        return ProjectCreateResponse(
            service.create(project)
        )
    }

    @PutMapping(value = ["/", ""])
    fun update(
        @RequestBody project: Project
    ): ProjectUpdateResponse {
        return ProjectUpdateResponse(
            updated = service.update(project)
        )
    }

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: Long
    ): ProjectDeleteResponse {
        return ProjectDeleteResponse(
            service.delete(id)
        )
    }
}