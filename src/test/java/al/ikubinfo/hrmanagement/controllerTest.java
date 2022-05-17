package al.ikubinfo.hrmanagement;
import al.ikubinfo.hrmanagement.converters.RoleConverter;
import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.*;
import al.ikubinfo.hrmanagement.repository.RequestRepository;
import al.ikubinfo.hrmanagement.repository.RoleRepository;
import al.ikubinfo.hrmanagement.services.DepartmentService;
import al.ikubinfo.hrmanagement.services.RequestService;
import al.ikubinfo.hrmanagement.services.RoleService;
import al.ikubinfo.hrmanagement.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class controllerTest extends TestSupport {

    private static final String URL_LOGIN = "/login";
    private static final String URL_REQUEST = "/requests/add";
    private static final  String URL_USER = "/users/add";
    private static final String URL_APPROVE_REQUEST = "/requests/{^[\\d]$}/accept";

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private RoleConverter roleConverter;

    @Autowired
    private DepartmentService departmentService;


    @Nested
    class AuthTests {
        @Test
        void validLogin() throws Exception {
            String token = userService.getTokenProvider().generateToken("admin@gmail.com", "password");
            mockMvc
                    .perform(
                            MockMvcRequestBuilders.post(URL_LOGIN)
                                    .header(AUTHORIZATION, BEARER + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(getLoginJson()))
                    .andExpect(status().isOk());
            assertNotNull(token);
        }

        private String getLoginJson() {
            LoginDto loginDto = new LoginDto("admin@gmail.com", "password");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(loginDto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    @Nested
    class ControllerTests {
        @Test
        void addNewRequestTest() throws Exception {
            String token = userService.getTokenProvider().generateToken("admin@gmail.com", "password");
            String requestJson = createJson(addRequest());
            mockMvc
                    .perform(
                            MockMvcRequestBuilders.post(URL_REQUEST)
                                    .header(AUTHORIZATION, BEARER + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestJson))
                    .andExpect(status().isOk());
        }


        @Test
        void addNewUserTest() throws Exception {
            String token = userService.getTokenProvider().generateToken("admin@gmail.com", "password");
            String requestJson = createJson(addUSer());
            mockMvc
                    .perform(
                            MockMvcRequestBuilders.post(URL_USER)
                                    .header(AUTHORIZATION, BEARER + token)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestJson))
                    .andExpect(status().isOk());
        }


        private RequestDto addRequest() {
            RequestDto requestDto = new RequestDto();
            requestDto.setReason("TEST");
            requestDto.setFromDate(LocalDate.now());
            requestDto.setToDate(LocalDate.now());
            requestDto.setBusinessDays(111);
            requestDto.setRequestStatus("TEST");
            requestDto.setDateCreated(LocalDate.now());
            requestDto.setDeleted(false);
            requestDto.setUser(userConverter.toMinimalUserDto(userConverter.toEntity(userService.getUserById(1L))));
            return requestDto;
        }

        private UserDto addUSer() {
            UserDto userDto = new UserDto();
            userDto.setFirstName("TEST");
            userDto.setLastName("TEST");
            userDto.setEmail("TEST");
            userDto.setPassword("TEST");
            userDto.setBirthday(new Date(2002 - 2 - 2));
            userDto.setGender("TEST");
            userDto.setHireDate(new Date(2020 - 2 - 2));
            userDto.setPaidTimeOff(25);
            userDto.setDeleted(false);
            userDto.setRole(roleService.getRoleById(1L));
            userDto.setDepartment(departmentService.getDepartmentById(1L));
            return userDto;
        }
    }
}


