package com.gustavoronchi.user_request.reader;

import com.gustavoronchi.user_request.domain.ResponseUser;
import com.gustavoronchi.user_request.dto.UserDTO;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FetchUserDataReaderConfig implements ItemReader<UserDTO> {

    private static Logger logger = LoggerFactory.getLogger(FetchUserDataReaderConfig.class);

    private final String BASE_URL = "http://localhost:8081";
    private RestTemplate restTemplate = new RestTemplate();
    private int page = 0;
    private List<UserDTO> users = new ArrayList<>();
    private int userIndex = 0;

    @Value("${chunkSize}")
    private int chunkSize;

    @Value("${pageSize}")
    private int pageSize;

    @Override
    public UserDTO read() {
        if (users.isEmpty()) {
            users = fetchUserDataFromAPI();
        }

        if (userIndex < users.size()) {
            return users.get(userIndex++);
        }

        return null;
    }

    private List<UserDTO> fetchUserDataFromAPI() {
        String uri = BASE_URL + "/clients/pagedData?page=%d&size=%d";
        logger.info("[READER STEP] Fetching data...");
        logger.info("[READER STEP] Request uri: " + String.format(uri, getPage(), pageSize));
        ResponseEntity<ResponseUser> response = restTemplate
                .exchange(String.format(uri, getPage(), pageSize), HttpMethod.GET, null, new ParameterizedTypeReference<ResponseUser>() {
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

    public void incrementPage() {
        this.page++;
    }

    @BeforeChunk
    public void beforeChunk() {
        for (int i = 0; i < chunkSize; i += pageSize) {
            users.addAll(fetchUserDataFromAPI());
        }
    }

    @AfterChunk
    public void afterChunk() {
        logger.info("Final chunk");
        incrementPage();
        userIndex = 0;
        users = new ArrayList<>();
    }
}
