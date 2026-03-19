package com.gustavoronchi.user_request.step;

import com.gustavoronchi.user_request.dto.UserDTO;
import com.gustavoronchi.user_request.entities.User;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class FetchUserDataAndStoreDBStepConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Value("${chunkSize}")
    private int chunkSize;

    @Bean
    public Step fetchUserDataAndStoreDBStep(ItemReader<UserDTO> fetchUserDataReader,
                                            ItemProcessor<UserDTO, User> selectFieldsUserDataProcessor,
                                            ItemWriter<User> insertUserDataDBWriter,
                                            JobRepository jobRepository) {
        return new StepBuilder("fetchUserDataAndStoreDBStep", jobRepository)
                .<UserDTO, User>chunk(chunkSize)
                .reader(fetchUserDataReader)
                .processor(selectFieldsUserDataProcessor)
                .writer(insertUserDataDBWriter)
                .transactionManager(transactionManager)
                .build();
    }
}
