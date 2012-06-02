package org.github.bbaron.springcfg.example.webapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigRepository extends JpaSpecificationExecutor<AppConfig>, JpaRepository<AppConfig, Long> {
}
