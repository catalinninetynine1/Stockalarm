package com.projectdevm8.stockalarm.repo;

import com.projectdevm8.stockalarm.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

  List<Alarm> findAllByIsActiveOrderByStockSymbol(Boolean isActive);
}
