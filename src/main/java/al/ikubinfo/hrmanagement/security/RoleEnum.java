package al.ikubinfo.hrmanagement.security;

public enum RoleEnum {


    HR("HR"),
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE"),
    PD("PD");

    final String roleName;

    RoleEnum(String role) {
        this.roleName = role;
    }
}

