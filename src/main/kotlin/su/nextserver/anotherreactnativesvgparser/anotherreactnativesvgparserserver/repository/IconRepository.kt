package su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import su.nextserver.anotherreactnativesvgparser.anotherreactnativesvgparserserver.entity.Icon


interface IconRepository : JpaRepository<Icon, Long>
