package cfg.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class AppConfigDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<AppConfig> data;

	@Autowired
    AppConfigRepository appConfigRepository;

	public AppConfig getNewTransientAppConfig(int index) {
        AppConfig obj = new AppConfig();
        setPropertyName(obj, index);
        setPropertyValue(obj, index);
        return obj;
    }

	public void setPropertyName(AppConfig obj, int index) {
        String propertyName = "propertyName_" + index;
        if (propertyName.length() > 100) {
            propertyName = new Random().nextInt(10) + propertyName.substring(1, 100);
        }
        obj.setPropertyName(propertyName);
    }

	public void setPropertyValue(AppConfig obj, int index) {
        String propertyValue = "propertyValue_" + index;
        if (propertyValue.length() > 100) {
            propertyValue = propertyValue.substring(0, 100);
        }
        obj.setPropertyValue(propertyValue);
    }

	public AppConfig getSpecificAppConfig(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        AppConfig obj = data.get(index);
        Long id = obj.getId();
        return appConfigRepository.findOne(id);
    }

	public AppConfig getRandomAppConfig() {
        init();
        AppConfig obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return appConfigRepository.findOne(id);
    }

	public boolean modifyAppConfig(AppConfig obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = appConfigRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'AppConfig' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<AppConfig>();
        for (int i = 0; i < 10; i++) {
            AppConfig obj = getNewTransientAppConfig(i);
            try {
                appConfigRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            appConfigRepository.flush();
            data.add(obj);
        }
    }
}
