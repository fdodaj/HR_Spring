package al.ikubinfo.hrmanagement.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static al.ikubinfo.hrmanagement.security.ApplicationUserPermission.*;

public enum ApplicationUserRoles {
    ADMIN(Sets.newHashSet(EMPLOYEE_READ, EMPLOYEE_WRITE, EMPLOYEE_DELETE, EMPLOYEE_EDIT, REQUEST_READ, REQUEST_WRITE, REQUEST_EDIT, REQUEST_DELETE,
            HOLIDAY_READ, HOLIDAY_WRITE, HOLIDAY_DELETE, HOLIDAY_EDIT, DEPARTMENT_READ, DEPARTMENT_WRITE, DEPARTMENT_EDIT, DEPARTMENT_DELETE)),
    EMPLOYEE(Sets.newHashSet()),
    DEPARTMENT_LEADER(Sets.newHashSet(REQUEST_READ, EMPLOYEE_READ, EMPLOYEE_WRITE)),
    HR(Sets.newHashSet());

    public final Set<ApplicationUserPermission> permissions;

    ApplicationUserRoles(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
