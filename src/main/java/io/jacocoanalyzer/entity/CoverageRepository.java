package io.jacocoanalyzer.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CoverageRepository extends CrudRepository<MethodCoverage, Integer> {
}
