package al.ikubinfo.hrmanagement;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = HrManagementApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TestSupport {
    protected static final String PASSWORD = "password";
    protected static final String BEARER = "Bearer ";
    protected static final String AUTHORIZATION = "Authorization";

    @Autowired
    protected MockMvc mockMvc;

    protected String createJson(Object dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
