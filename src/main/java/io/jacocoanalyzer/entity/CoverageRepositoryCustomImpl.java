package io.jacocoanalyzer.entity;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CoverageRepositoryCustomImpl implements CoverageRepositoryCustom {
  @PersistenceContext EntityManager entityManager;

  @Override public List<PackageSumCoverage> sumByPackage() {
    // Create a fake ID column with row_number().
    var query = entityManager.createNativeQuery(
        "select row_number() over (order by package_name) as id, coverage_run_name, package_name, "
            + "sum(instructions_missed) as instructions_missed, sum(instructions_covered) as instructions_covered,"
            + "sum(complexity_missed) as complexity_missed, "
            + "sum(complexity_covered) as complexity_covered, sum(lines_missed) as lines_missed,"
            + "sum(lines_covered) as lines_covered, sum(method_missed) as method_missed, "
            + "sum(method_covered) as method_covered "
            + "from method_coverage group by package_name, coverage_run_name;",
        PackageSumCoverage.class);
    //noinspection unchecked
    return query.getResultList();

    //    List<PackageSumCoverage> result = new ArrayList<>();
    //    query.getResultList().forEach(method -> {
    //      PackageSumCoverage packageSumCoverage = new PackageSumCoverage((MethodCoverage) method);
    //      result.add(packageSumCoverage);
    //    });
    //    return result;
  }
}
