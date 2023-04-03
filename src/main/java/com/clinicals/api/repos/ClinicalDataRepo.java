package com.clinicals.api.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinicals.api.model.ClinicalData;

public interface ClinicalDataRepo extends JpaRepository<ClinicalData, Integer> {

}
