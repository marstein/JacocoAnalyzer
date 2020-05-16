package io.jacocoanalyzer;

import io.jacocoanalyzer.entity.CoverageRepository;
import io.jacocoanalyzer.entity.CoverageRepositoryCustom;
import io.jacocoanalyzer.web.application.CoverageAnalysisController;
import java.io.File;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootVueApplicationTests {
  @Test public void contextLoads() {
  }

  @Autowired FileUploaderBean fileUploaderBean;
  @Autowired CoverageRepository coverageRepository;
  @Autowired CoverageRepositoryCustom coverageRepositoryCustom;

  @Before public void loadFile() {
    fileUploaderBean.readAndUploadFile(new File("src/test/resources/sample.xml"), "test run");
  }

  @After public void cleanUp() {
    coverageRepository.deleteAll();
  }

  @Test public void loadFileAndRead() {
    coverageRepository.findAll().forEach(methodCoverage -> {
      Assert.assertEquals("test run", methodCoverage.getCoverageRunName());
      Assertions.assertThat(methodCoverage.getClassName()).startsWith("io/redlock/rql/condition/base/");
    });
  }

  @Test public void testDelete() {
    coverageRepositoryCustom.deleteCoverageRun("test run");
    coverageRepository.findAll().forEach(p -> Assert
        .fail("this should never be reached because all entries were " + "deleted"));
  }

  @Autowired CoverageAnalysisController coverageAnalysisController;

/*
  @Test public void testController() {
    Model m = new BindingAwareModelMap();
    coverageAnalysisController.getCoverageAnalysis(m);
    m.containsAttribute("coverages")
  }
*/
/*
  @Test public void testSumUp() {
    List<PackageSumCoverage> covs = coverageRepositoryCustom.sumByPackage();
    Assert.assertEquals(1, covs.size());
    Assert.assertEquals(1, covs.get(0).getCrapsum());
  }
*/
}
