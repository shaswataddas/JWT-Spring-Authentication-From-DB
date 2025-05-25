package com.jwt.authentication.jwtAuthentication.services;

import com.jwt.authentication.jwtAuthentication.entities.Train;
import com.jwt.authentication.jwtAuthentication.repositories.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    private TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Train findByTrainName(String trainName) {
        return trainRepository.findByTrainName(trainName);
    }

    public List<Train> findByTrainId(List<Integer> trainIdList) {
        return trainRepository.findByTrainIdIn(trainIdList);
    }
}
