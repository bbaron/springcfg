package org.github.bbaron.springcfg.example.webapp.model;

import java.util.List;

import org.github.bbaron.springcfg.example.webapp.config.MainCfg;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;


@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = MainCfg.class)
@Transactional
public class AppConfigIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private AppConfigDataOnDemand dod;

	@Autowired
    AppConfigRepository appConfigRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to initialize correctly", dod.getRandomAppConfig());
        long count = appConfigRepository.count();
        Assert.assertTrue("Counter for 'AppConfig' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        AppConfig obj = dod.getRandomAppConfig();
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to provide an identifier", id);
        obj = appConfigRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AppConfig' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'AppConfig' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to initialize correctly", dod.getRandomAppConfig());
        long count = appConfigRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'AppConfig', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<AppConfig> result = appConfigRepository.findAll();
        Assert.assertNotNull("Find all method for 'AppConfig' illegally returned null", result);
        Assert.assertTrue("Find all method for 'AppConfig' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to initialize correctly", dod.getRandomAppConfig());
        long count = appConfigRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<AppConfig> result = appConfigRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'AppConfig' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'AppConfig' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        AppConfig obj = dod.getRandomAppConfig();
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to provide an identifier", id);
        obj = appConfigRepository.findOne(id);
        Assert.assertNotNull("Find method for 'AppConfig' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAppConfig(obj);
        Integer currentVersion = obj.getVersion();
        appConfigRepository.flush();
        Assert.assertTrue("Version for 'AppConfig' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveUpdate() {
        AppConfig obj = dod.getRandomAppConfig();
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to provide an identifier", id);
        obj = appConfigRepository.findOne(id);
        boolean modified =  dod.modifyAppConfig(obj);
        Integer currentVersion = obj.getVersion();
        AppConfig merged = appConfigRepository.save(obj);
        appConfigRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'AppConfig' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to initialize correctly", dod.getRandomAppConfig());
        AppConfig obj = dod.getNewTransientAppConfig(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'AppConfig' identifier to be null", obj.getId());
        appConfigRepository.save(obj);
        appConfigRepository.flush();
        Assert.assertNotNull("Expected 'AppConfig' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        AppConfig obj = dod.getRandomAppConfig();
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'AppConfig' failed to provide an identifier", id);
        obj = appConfigRepository.findOne(id);
        appConfigRepository.delete(obj);
        appConfigRepository.flush();
        Assert.assertNull("Failed to remove 'AppConfig' with identifier '" + id + "'", appConfigRepository.findOne(id));
    }
}
