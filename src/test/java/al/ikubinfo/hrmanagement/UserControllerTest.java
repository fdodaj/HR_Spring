package al.ikubinfo.hrmanagement;
import al.ikubinfo.hrmanagement.dto.LoginDto;
import al.ikubinfo.hrmanagement.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends TestSupport {

    private static final String URL_LOGIN = "/login";
    @Autowired private UserService userService;

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
}

