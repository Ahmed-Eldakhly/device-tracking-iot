package com.vodafone.vodafonetask.repository;

import com.vodafone.vodafonetask.model.Devices;
import com.vodafone.vodafonetask.model.Sim;
import com.vodafone.vodafonetask.model.SimStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.util.List;

public interface SimRepository extends JpaRepository<Sim, Long> {

}