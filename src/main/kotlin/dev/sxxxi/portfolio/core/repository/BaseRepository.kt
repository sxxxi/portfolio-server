package dev.sxxxi.portfolio.core.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
@NoRepositoryBean
interface BaseRepository<T, ID> : PagingAndSortingRepository<T, ID>, CrudRepository<T, ID>
