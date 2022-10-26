package org.example.database.springdata.mongodb;

import org.example.data.FundDataMongo;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

@Profile("mongoDB")
public interface FundDataMongoRepository extends MongoRepository<FundDataMongo, Long> {
}
