package dev.sxxxi.portfolio.projects

import dev.sxxxi.portfolio.projects.domain.Project

interface ProjectService {

    /**
     * @param instance A new project instance
     * @return the created project for the client to display
     */
    fun create(instance: ProjectEntity): Project

    /**
     * @param instance Updated project instance
     * @return the updated project for the client to display
     */
    fun update(instance: ProjectEntity): Project

    /**
     * @return all project
     */
    fun getAll(): List<Project>

    /**
     * @param id product ID
     * @return found item
     */
    fun getById(id: Long): Project

    /**
     * @param id ID of item to be deleted
     * @return deleted item's ID
     */
    fun deleteById(id: Long): Long
}
