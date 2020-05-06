package io.redlock;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CoverageXMLReaderTest {

  private class TestRepo implements ICoverageRepository{
    List<MethodCoverage> methodCoverageList = new ArrayList<>();
    @Override public void write(MethodCoverage methodCoverage) {
      methodCoverageList.add(methodCoverage);
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
