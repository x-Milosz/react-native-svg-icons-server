package name.milosznowaczyk.reactnativesvgicons.server.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "icons")
@EntityListeners(AuditingEntityListener::class)
data class Icon(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(name = "name", unique = true, nullable = false)
    var name: String,

    @CreatedDate
    @Column(name = "creation_date", nullable = false)
    var creationDate: Date,

    @LastModifiedDate
    @Column(name = "last_modification_date", nullable = false)
    var lastModificationDate: Date,
)
