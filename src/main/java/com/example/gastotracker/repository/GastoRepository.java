package com.example.gastotracker.repository;

import com.example.gastotracker.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepository extends JpaRepository<Gasto, Long> {}
