package org.example.infra.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "publish_date")
    private Timestamp publishDate;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public CommentEntity() {
    }
}
