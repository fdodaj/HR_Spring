package al.ikubinfo.hrmanagement.security;

public enum ApplicationUserPermission {
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),
    EMPLOYEE_DELETE("employee:delete"),
    EMPLOYEE_EDIT("employee:edit"),


    REQUEST_READ("request:read"),
    REQUEST_WRITE("request:write"),
    REQUEST_DELETE("request:delete"),
    REQUEST_EDIT("request:edit"),

    HOLIDAY_READ("holiday:read"),
    HOLIDAY_WRITE("holiday:write"),
    HOLIDAY_DELETE("holiday:write"),
    HOLIDAY_EDIT("holiday:edit"),

    DEPARTMENT_READ("department:read"),
    DEPARTMENT_WRITE("department:write"),
    DEPARTMENT_DELETE("department:delete"),
    DEPARTMENT_EDIT("department:edit");


    private final String permissions;
    ApplicationUserPermission(String permissions) {
        this.permissions = permissions;
    }
    public String getPermission(){
        return permissions;
    }
}
