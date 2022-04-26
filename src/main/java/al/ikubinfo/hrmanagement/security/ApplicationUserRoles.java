package al.ikubinfo.hrmanagement.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static al.ikubinfo.hrmanagement.security.ApplicationUserPermission.*;

public enum ApplicationUserRoles {
    ADMIN(Sets.newHashSet(EMPLOYEE_READ, EMPLOYEE_WRITE, EMPLOYEE_DELETE,EMPLOYEE_EDIT, REQUEST_READ, REQUEST_WRITE, REQUEST_EDIT, REQUEST_DELETE,
            HOLIDAY_READ,HOLIDAY_WRITE, HOLIDAY_DELETE, HOLIDAY_EDIT, DEPARTMENT_READ, DEPARTMENT_WRITE, DEPARTMENT_EDIT, DEPARTMENT_DELETE)),
    EMPLOYEE(Sets.newHashSet()),
    DEPARTMENT_LEADER(Sets.newHashSet(REQUEST_WRITE, REQUEST_READ)),
    HR(Sets.newHashSet());

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRoles(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
