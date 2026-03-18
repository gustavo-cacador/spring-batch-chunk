package com.gustavoronchi.user_request.reader;

import com.gustavoronchi.user_request.domain.ResponseUser;
import com.gustavoronchi.user_request.dto.UserDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class FetchUserDataReaderConfig implements ItemReader<UserDTO> {

    private final String BASE_URL = "http://localhost:8081";
    private RestTemplate restTemplate = new RestTemplate();
    private int page = 0;

    @Override
    public @Nullable UserDTO read() throws Exception {
        return null;
    }

    private List<UserDTO> fetchUserDataFromAPI() {
        String uri = BASE_URL + "/clients/pagedData?page=%d&size=%d";
        ResponseEntity<ResponseUser> response = restTemplate
                .exchange(String.format(uri, getPage()), HttpMethod.GET, null, new ParameterizedTypeReference<ResponseUser>() {
        });
        return response.getBody().getContent();
    }

    // testar essa função reduzida
//    private List<UserDTO> fetchUserDataFromAPI() {
//        var response = restTemplate.exchange(
//                BASE_URL + "/clients/pagedData?page=%d&size=%d".formatted(getPage(), getSize()),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<ResponseUser>() {}
//        );
//
//        return response.getBody().getContent();
//    }

    public int getPage() {
        return page;
    }
}
