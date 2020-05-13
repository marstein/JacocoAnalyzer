package io.jacocoanalyzer.entity;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CoverageRepositoryCustomImpl implements CoverageRepositoryCustom {
  @PersistenceContext EntityManager entityManager;

  /**
   * CRAP(m) = comp(m)^2 * (1 – cov(m)/100)^3 + comp(m)
   *
   * @return Thymeleaf HTML page with a nice table
   */
  @Override public List<PackageSumCoverage> sumByPackage() {
    // Create a fake ID column with row_number().
    // Sums up over package, class.
    String COV_GROUP_QUERY = "select row_number() over (order by package_name) as id,\n" + "       coverage_run_name,\n"
        + "       package_name,\n" + "       sum(lines_missed)                         as linesmissedsum,\n"
        + "       sum(lines_covered)                        as linescoveredsum,\n"
        + "       cast(sum(lines) as int)                   as linesum,\n"
        + "       cast(sum(crap) as int)                    as crapsum,\n"
        + "       sum(comp)                                 as compsum,\n"
        + "       avg(cov) * 100                            as covavgpercent\n" + "from (\n"
        + "         select coverage_run_name,\n" + "                package_name,\n" + "                class_name,\n"
        + "                method_name,\n" + "                lines_missed,\n" + "                lines_covered,\n"
        + "                cov,\n" + "                lines,\n" + "                comp,\n"
        + "                comp * comp * (1 - cov) ^ 3 + comp as crap\n" + "         from (select coverage_run_name,\n"
        + "                      package_name,\n" + "                      class_name,\n"
        + "                      method_name,\n" + "                      lines_covered,\n"
        + "                      lines_missed,\n"
        + "                      complexity_missed + complexity_covered                                       as "
        + "comp,\n"
        + "                      cast(lines_covered as float) / cast((lines_missed + lines_covered) as float) as cov,\n"
        + "                      lines_missed + lines_covered                                                 as "
        + "lines\n" + "               from method_coverage as calc_comp\n"
        + "               order by package_name, class_name\n" + "              ) as per_method) as per_package\n"
        + "group by coverage_run_name, package_name\n" + "order by linesum;";
    var query = entityManager.createNativeQuery(COV_GROUP_QUERY, PackageSumCoverage.class);
    //noinspection unchecked
    return query.getResultList();
  }

  // delete where coverage_run_name = :coverageRunName
  public void deleteCoverageRun(String coverageRunName) {
    Query q = entityManager.createNativeQuery("DELETE FROM method_coverage where coverage_run_name = :coverage");
    q.setParameter("coverage", "test run");
    q.executeUpdate();
  }
}
