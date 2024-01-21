package com.icsd19080_icsd19235;


import java.util.stream.Collectors;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.icsd19080_icsd19235.Paper.PaperState;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    // getters and setters

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public enum RoleName {
        VISITOR,
        AUTHOR,
        PC_MEMBER,
        PC_CHAIR
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleName roleName;

    public void setState(RoleName roleName) {
        this.roleName = roleName;
    }
    
    public RoleName getState() {
        return roleName;
    }


    public static List<Paper> applyRoleBasedFilter(List<Paper> papers, User user) {
        if (user != null && user.getRoles() != null) {
            Set<RoleName> userRoles = user.getRoles().stream()
                    .map(Role::getState)
                    .collect(Collectors.toSet());

            if (userRoles.contains(RoleName.VISITOR)) {
                return papers.stream()
                    .filter(paper -> paper.getState() == PaperState.ACCEPTED)
                    .collect(Collectors.toList());
            } else if (userRoles.contains(RoleName.AUTHOR)) {
                // Implement AUTHOR role filtering logic
            } else if (userRoles.contains(RoleName.PC_CHAIR)) {
                // Implement PC_CHAIR role filtering logic
            } else if (userRoles.contains(RoleName.PC_MEMBER)) {
                // Implement PC_MEMBER role filtering logic
            }

            // Return the filtered list of papers
            return papers;
        } else {
            // If user or roles are null, return the original list of papers
            return papers;
        }
    }
}

