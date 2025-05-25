package com.jwt.authentication.jwtAuthentication.repositories;

import com.jwt.authentication.jwtAuthentication.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Integer> {
    Train findByTrainName(String trainName);

    List<Train> findByTrainIdIn(List<Integer> trainIds);
}
