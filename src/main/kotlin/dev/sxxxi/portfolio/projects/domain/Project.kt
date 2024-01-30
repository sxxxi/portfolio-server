package dev.sxxxi.portfolio.projects.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Project (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var title: String,
    var description: String,
    var repoLink: String?,
    var deployLink: String?,
    var imageLinks: List<String>
)