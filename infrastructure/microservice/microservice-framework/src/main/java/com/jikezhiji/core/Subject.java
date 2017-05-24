package com.jikezhiji.core;

import java.security.Permissions;
import java.security.Principal;
import java.util.*;

/**
 * Created by E355 on 2016/9/12.
 */
public class Subject {

    private Visitor primary;

    private Set<Principal> secondaries = new HashSet<>();

    private Set<Role> roles = new HashSet<>();

    public Visitor getPrimary() {
        return primary;
    }

    public void setPrimary(Visitor primary) {
        this.primary = primary;
    }

    public Set<Principal> getSecondaries() {
        return secondaries;
    }

    public void addPrincipals(Principal ... roles) {
        this.secondaries.addAll(Arrays.asList(roles));
    }

    public void addPrincipals(Collection<Principal> roles) {
        this.secondaries.addAll(roles);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean hasRole(String roleCode) {
        return roles.stream().anyMatch(role -> role.getRoleCode().equals(roleCode));
    }

    public void addRoles(Role ... roles) {
        this.roles.addAll(Arrays.asList(roles));
    }

    public void addRoles(Collection<Role> roles) {
        this.roles.addAll(roles);
    }

    public Subject(Visitor primary) {
        this.primary = primary;
    }

    public Subject(Visitor primary,Set<Role> roles) {
        this(primary);
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;

        Subject subject = (Subject) o;

        if(getPrimary() == null || subject.getPrimary() == null){
            return false;
        }

        return getPrimary().equals(subject.getPrimary());

    }

    @Override
    public int hashCode() {
        return getPrimary().hashCode();
    }

    /**
     * Created by E355 on 2016/9/12.
     */
    public static class Role  {
        private String roleCode;
        private Permissions permissions;

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public Permissions getPermissions() {
            return permissions;
        }

        public void setPermissions(Permissions permissions) {
            this.permissions = permissions;
        }

        public Role(String roleCode) {
            this.roleCode = roleCode;
        }

    }

    /**
     * Created by E355 on 2016/8/24.
     */
    public static class Visitor implements Principal {
        private final String identifiers;

        private Map<String,Object> attributes = new HashMap<>();


        @Override
        public String getName() {
            return getIdentifiers();
        }

        public String getIdentifiers(){
            return identifiers;
        }


        public void setAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public Object getAttribute(String key) {
            return attributes.get(key);
        }

        public void setAttribute(String key,Object value) {
            attributes.put(key,value);
        }

        public Visitor(String id) {
            this.identifiers = id;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Visitor)) return false;

            Visitor user = (Visitor) o;

            return getIdentifiers().equals(user.getIdentifiers());

        }

        @Override
        public int hashCode() {
            return getIdentifiers().hashCode();
        }
    }
}
