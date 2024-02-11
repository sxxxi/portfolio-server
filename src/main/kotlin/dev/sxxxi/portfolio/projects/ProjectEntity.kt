package dev.sxxxi.portfolio.projects

import jakarta.persistence.*

@Entity
class ProjectEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var title: String,

    @Column
    var description: String,

    @Column
    var repoLink: String? = null,

    @Column
    var deployLink: String? = null,

    @Column
    var imagePaths: List<String> = listOf()
)