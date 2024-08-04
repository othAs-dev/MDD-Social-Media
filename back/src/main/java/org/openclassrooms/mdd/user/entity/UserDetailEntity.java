package org.openclassrooms.mdd.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.utils.entity.BaseEntity;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "user_detail")
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "email", unique = true)
    @Email
    @NotNull
    private String email;

    @Size(min = 8, max = 32)
    @NotEmpty
    @NotNull
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
