package io.redlock;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.stream.XMLStreamException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CoverageXMLReaderTest {

  private static class TestRepo<T> implements CoverageRepository{
    public List<T> methodCoverageList = new ArrayList<>();

    @Override public <S extends MethodCoverage> S save(S entity) {
      methodCoverageList.add((T) entity);
      return entity;
    }

    @Override public <S extends MethodCoverage> Iterable<S> saveAll(Iterable<S> entities) {
      return null;
    }

    @Override public Optional<MethodCoverage> findById(Integer integer) {
      return Optional.empty();
    }

    @Override public boolean existsById(Integer integer) {
      return false;
    }

    @Override public Iterable<MethodCoverage> findAll() {
      return null;
    }

    @Override public Iterable<MethodCoverage> findAllById(Iterable<Integer> integers) {
      return null;
    }

    @Override public long count() {
      return 0;
    }

    @Override public void deleteById(Integer integer) {

    }

    @Override public void delete(MethodCoverage entity) {

    }

    @Override public void deleteAll(Iterable<? extends MethodCoverage> entities) {

    }

    @Override public void deleteAll() {

    }
  }

  @Test void readFromXML() throws XMLStreamException {
    TestRepo testRepo = new TestRepo();
    CoverageXMLReader c = new CoverageXMLReader(testRepo);
    c.readFromXML(Thread.currentThread().getContextClassLoader().getResourceAsStream("sample.xml"));
    Assertions.assertEquals(testRepo.methodCoverageList.size(), 58);
    Assertions.assertEquals("MethodCoverage{coverageRunName='redlock-search', packageName='io/redlock/rql/condition/base', "
        + "className='io/redlock/rql/condition/base/AbstractIntMembershipCondition', methodName='<init>', "
        + "instructionsCovered=0, instructionsMissed=4, methodCovered=0, methodMissed=1, linesCovered=0, "
        + "linesMissed=2, complexityCovered=0, complexityMissed=1}", testRepo.methodCoverageList.get(3).toString());
  }
}
