package io.jacocoanalyzer.entity;

import com.google.common.base.Strings;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CoverageRepositoryCustomImpl implements CoverageRepositoryCustom {
  @PersistenceContext EntityManager entityManager;
  public static Logger log = LoggerFactory.getLogger(CoverageRepositoryCustomImpl.class);

  /**
   * CRAP(m) = comp(m)^2 * (1 – cov(m)/100)^3 + comp(m)
   *
   * @param runName
   * @return Thymeleaf HTML page with a nice table
   */
  @Override public List<ReportSumCoverage> sumByPackage(String runName) {
    // Create a fake ID column with row_number().
    // Sums up over package, class.
    if (Strings.isNullOrEmpty(runName)) {
      runName = "%";
    }
    log.info("query run {}", runName);
    String COV_GROUP_QUERY =
        "select row_number() over (order by coverage_run_name) as id,\n" + "       coverage_run_name,\n"
            + "      report_name,\n" + "       sum(lines_missed)                        as linesmissedsum,\n"
            + "       sum(lines_covered)                       as linescoveredsum,\n"
            + "       cast(sum(lines) as int)                  as linesum,\n"
            + "       cast(sum(crap) as int8)                   as crapsum,\n"
            + "       sum(comp)                                as compsum,\n"
            + "       ''                                as package_name,\n"
            + "       ''                                as method_name,\n"
            + "       ''                                as class_name,\n"
            + "       avg(cov) * 100                           as covavgpercent\n" + "from (\n"
            + "         select coverage_run_name, -- as per_method\n" + "                package_name,\n"
            + "                report_name,\n" + "                class_name,\n" + "                method_name,\n"
            + "                lines_missed,\n" + "                lines_covered,\n" + "                cov,\n"
            + "                lines,\n" + "                comp,\n"
            + "                (comp * comp * (1 - cov) ^ 3 + comp) as crap\n" + "         from (\n"
            + "                  select coverage_run_name, -- as calc_comp\n"
            + "                         report_name,\n" + "                         package_name,\n"
            + "                         class_name,\n" + "                         method_name,\n"
            + "                         (lines_covered)                                                              "
            + "  as lines_covered,\n"
            + "                         (lines_missed)                                                               "
            + "  as lines_missed,\n"
            + "                         (complexity_missed + complexity_covered)                                     "
            + "  as comp,\n"
            + "                         (lines_covered / (cast (lines_missed + lines_covered as float))) as cov,\n"
            + "                         (lines_missed + lines_covered)                                               "
            + "  as lines\n" + "                  from method_coverage\n" + "                           as calc_comp\n"
            + "                  where coverage_run_name LIKE :coverage_run_name\n"
            + "                  --where package_name='io/redlock/rql' and class_name='io/redlock/rql/RqlTypeUtils'\n"
            + "                  --group by coverage_run_name, report_name, package_name, class_name, method_name\n"
            + "              ) as per_method\n" + "     ) as per_report\n" + "-- where report_name='redlock-search'\n"
            + "group by coverage_run_name, report_name";
    var query = entityManager.createNativeQuery(COV_GROUP_QUERY, ReportSumCoverage.class).setParameter(
        "coverage_run_name",
        runName);
    return query.getResultList();
  }


  /**
   * @param runName
   * @param limit
   * @return coverages by method
   */
  @Override public List<ReportSumCoverage> crappyMethods(String runName, Integer limit) {
    if (Strings.isNullOrEmpty(runName)) {
      runName = "%";
    }
    if (limit == null || limit == 0) {
      limit = 20;
    }
    log.info("query run {}", runName);
    String COV_GROUP_QUERY =
        "select row_number() over (order by coverage_run_name) as id,\n" + "       coverage_run_name,\n"
            + "       report_name,\n" + "       package_name, class_name, method_name,\n"
            + "       sum(lines_missed)                              as linesmissedsum,\n"
            + "       sum(lines_covered)                             as linescoveredsum,\n"
            + "       cast(sum(lines) as int)                        as linesum,\n"
            + "       sum(comp)                                      as compsum,\n"
            + "       avg(cov) * 100                                 as covavgpercent,\n"
            + "       cast(sum(crap) as int8)                        as crapsum,\n" + "       avg(crap) as crapAvg\n"
            + "from (\n" + "         select coverage_run_name, -- as per_method\n" + "                package_name,\n"
            + "                report_name,\n" + "                class_name,\n" + "                method_name,\n"
            + "                lines_missed,\n" + "                lines_covered,\n" + "                cov,\n"
            + "                lines,\n" + "                comp,\n"
            + "                (comp * comp * (1 - cov) ^ 3 + comp) as crap\n" + "         from (\n"
            + "                  select coverage_run_name, -- as calc_comp\n"
            + "                         report_name,\n" + "                         package_name,\n"
            + "                         class_name,\n" + "                         method_name,\n"
            + "                         (lines_covered)                                                 as "
            + "lines_covered,\n"
            + "                         (lines_missed)                                                  as "
            + "lines_missed,\n"
            + "                         (complexity_missed + complexity_covered)                        as comp,\n"
            + "                         (lines_covered / (cast(lines_missed + lines_covered as float))) as cov,\n"
            + "                         (lines_missed + lines_covered)                                  as lines\n"
            + "                  from method_coverage\n" + "                           as calc_comp\n"
            + "                       where lines_missed+lines_covered > 1\n"
            + "                             and coverage_run_name LIKE :coverage_run_name\n"
            + "--                          and class_name='io/redlock/rql/generated/RqlConfigParser'\n"
            + "                         -- AND report_name in\n"
            + "--                         ('redlock-dal', 'redlock-event-collector', 'redlock-ir-module', "
            + "'redlock-java-common', 'redlock-maven-agg',\n"
            + "--                          'redlock-public-rest-api', 'redlock-query-optimizer', "
            + "'redlock-redshift-sync-service', 'redlock-rules-eval',\n"
            + "--                          'redlock-search')\n"
            + "                  group by coverage_run_name, report_name, package_name, class_name, method_name, "
            + "lines_covered, lines_missed, comp, cov, lines\n" + "              ) as per_method\n"
            + "     ) as per_report\n" + "     -- where report_name='redlock-search'\n"
            + "group by coverage_run_name, report_name, package_name, class_name, method_name\n"
            + "order by crapsum desc limit :limit\n";
    var query = entityManager.createNativeQuery(COV_GROUP_QUERY, ReportSumCoverage.class).setParameter(
        "coverage_run_name",
        runName).setParameter("limit", limit);
    return query.getResultList();
  }

  // delete where coverage_run_name = :coverageRunName
  public void deleteCoverageRun(String coverageRunName) {
    Query q = entityManager.createNativeQuery("DELETE FROM method_coverage where coverage_run_name = :coverage");
    q.setParameter("coverage", "test run");
    q.executeUpdate();
  }
}
