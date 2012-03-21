package cfg.model;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
public class AppConfig {

    @Column(unique = true)
    @Size(min = 1, max = 100)
    private String propertyName;

    @Size(max = 100)
    private String propertyValue;
}
