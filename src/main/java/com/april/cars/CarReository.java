package com.april.cars;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Cars, Long>
{
}