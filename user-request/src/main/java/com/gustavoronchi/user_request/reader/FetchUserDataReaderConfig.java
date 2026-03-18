package com.gustavoronchi.user_request.reader;

import com.gustavoronchi.user_request.dto.UserDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class FetchUserDataReaderConfig implements ItemReader<UserDTO> {

    private final String BASE_URL = "http://localhost:8081";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public @Nullable UserDTO read() throws Exception {
        return null;
    }

    private List<UserDTO> fetchUserDataFromAPI() {
        String uri = BASE_URL + "/clients/pagedData?page=%d&size=%d";
        return List.of();
    }
}
