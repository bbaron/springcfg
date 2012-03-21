package cfg.model;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = AppConfig.class)
public interface AppConfigRepository {
}
