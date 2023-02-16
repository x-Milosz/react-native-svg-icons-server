package name.milosznowaczyk.reactnativesvgicons.server.repository

import name.milosznowaczyk.reactnativesvgicons.server.entity.Icon
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface IconRepository : JpaRepository<Icon, Long> {
    fun findByOrderByNameAsc(pageable: Pageable): Page<Icon>

    fun findByNameContainsAllIgnoreCaseOrderByNameAsc(name: String, pageable: Pageable): Page<Icon>

}
