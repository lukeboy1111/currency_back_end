package com.lukec.currency.jpa.repository;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lukec.currency.entity.ConversionHistory;

public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, BigInteger> {

}
