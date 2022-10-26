package org.example.database.springdata.jpa;

import org.example.data.FundData;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("!mongoDB")
public interface FundDataJpaRepository extends JpaRepository<FundData, Long> {
}
