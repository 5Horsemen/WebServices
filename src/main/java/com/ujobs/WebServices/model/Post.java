package com.ujobs.WebServices.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column
        private String type;

        @Column(length = 50)
        private String title;

        @Column(length = 5000)
        private String content;

        @Lob
        @Column
        private byte[] image;

        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
        private List<Comment> comments;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToMany
        @JoinTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private Set<User> likes;

        @ManyToMany
        @JoinTable(name = "post_shares", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private Set<User> shares;

}
