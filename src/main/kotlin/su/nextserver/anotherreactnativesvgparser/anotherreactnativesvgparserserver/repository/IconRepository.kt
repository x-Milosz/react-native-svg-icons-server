package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.entity.Icon


interface IconRepository : JpaRepository<Icon, Long> {
    fun findByOrderByNameAsc(pageable: Pageable): Page<Icon>

    fun findByNameContainsAllIgnoreCaseOrderByNameAsc(name: String, pageable: Pageable): Page<Icon>
}
