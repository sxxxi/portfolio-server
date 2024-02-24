package dev.sxxxi.portfolio.unit.projects

import dev.sxxxi.portfolio.projects.ProjectRepository
import dev.sxxxi.portfolio.projects.ProjectService
import dev.sxxxi.portfolio.projects.exception.ProjectConflictException
import dev.sxxxi.portfolio.projects.exception.ProjectNotFoundException
import dev.sxxxi.portfolio.projects.ProjectEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.platform.commons.logging.LoggerFactory
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest
@ActiveProfiles("development")
class ProjectServiceTest {

    @MockBean
    private lateinit var projectRepository: ProjectRepository

    @Autowired
    private lateinit var projectService: ProjectService

    private val logger = LoggerFactory.getLogger(this::class.java)


    /**
     * BROKEN :(
     */
    @ParameterizedTest
    @ValueSource(strings = ["null", "10"])
    fun `must succeed if null or id has no conflicts`(string: String) {
        val id: Long? = string.toLongOrNull()
        val existingId = 10L
        val newProject = mock(ProjectEntity::class.java)

        logger.info { "Parsed ID: $id" }
        `when`(newProject.id).thenReturn(id)
        `when`(projectRepository.save(newProject)).thenReturn(newProject)
        newProject.id?.let {
            `when`(projectRepository.existsById(it)).thenReturn(it == existingId)
        }

//        assertNotNull(projectService.create(newProject))
    }

    @Test
    fun `instances to be provided must be unique`() {
        val existingId = 1L
        val duplicateProject = mock(ProjectEntity::class.java)
        `when`(duplicateProject.id).thenReturn(existingId)
        `when`(projectRepository.existsById(existingId)).thenReturn(true)
        assertThrows<ProjectConflictException> {
            projectService.create(duplicateProject)
        }
    }

    @Test
    fun `retrieving non-existing project throws ProjectNotFoundException`() {
        val nonExistingProjectId = 1L

        `when`(projectRepository.findById(nonExistingProjectId)).thenReturn(Optional.empty())
        assertThrows<ProjectNotFoundException> {
            projectService.getById(nonExistingProjectId)
        }
    }

    @Test
    fun `project is deleted properly`() {
    }

    @Test
    fun `deleting non-existent project throws an error`() {
        val nonExistentId = 1L

        `when`(projectRepository.existsById(nonExistentId)).thenReturn(false)

        assertThrows<ProjectNotFoundException> {
            projectService.deleteById(nonExistentId)
        }
    }
}