package io.jacocoanalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.stream.XMLStreamException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CoverageXMLReaderTest {

  private static class TestRepo<MethodCoverage, Integer> implements CoverageRepository {
    public List<MethodCoverage> methodCoverageList = new ArrayList<>();

    @Override public <S extends io.jacocoanalyzer.MethodCoverage> S save(S entity) {
      methodCoverageList.add((MethodCoverage) entity);
      return entity;
    }

    @Override public <S extends io.jacocoanalyzer.MethodCoverage> Iterable<S> saveAll(Iterable<S> entities) {
      return null;
    }

    @Override public Optional<io.jacocoanalyzer.MethodCoverage> findById(java.lang.Integer integer) {
      return Optional.empty();
    }

    @Override public boolean existsById(java.lang.Integer integer) {
      return false;
    }

    @Override public Iterable<io.jacocoanalyzer.MethodCoverage> findAll() {
      return null;
    }

    @Override public Iterable<io.jacocoanalyzer.MethodCoverage> findAllById(Iterable<java.lang.Integer> integers) {
      return null;
    }

    @Override public long count() {
      return 0;
    }

    @Override public void deleteById(java.lang.Integer integer) {

    }

    @Override public void delete(io.jacocoanalyzer.MethodCoverage entity) {

    }

    @Override public void deleteAll(Iterable<? extends io.jacocoanalyzer.MethodCoverage> entities) {

    }

    @Override public void deleteAll() {

    }
  }

  @Test void readFromXML() throws XMLStreamException {
    TestRepo<MethodCoverage, Integer> testRepo = new TestRepo<>();
    CoverageXMLReader c = new CoverageXMLReader(testRepo);
    c.readFromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("sample.xml"), "sprint test");
    Assertions.assertEquals(testRepo.methodCoverageList.size(), 58);
    MethodCoverage thirdCoverageMethod = testRepo.methodCoverageList.get(3);
    Assertions.assertEquals("io/redlock/rql/condition/base/AbstractIntMembershipCondition",
        thirdCoverageMethod.getClassName(),
        "class name");
    Assertions.assertEquals("<init>",
        thirdCoverageMethod.getMethodName(),
        "method name " + thirdCoverageMethod.toString());
    Assertions.assertEquals(1,
        thirdCoverageMethod.getComplexityMissed(),
        "complexity missed " + thirdCoverageMethod.toString());
  }
}
