package com.VU.PSKProject.Service.LearningTree;

import com.VU.PSKProject.Service.Model.CoveredTopicDTO;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface LearningTreeService {
    List<CoveredTopicDTO> getAllWorkerCoveredTopics(Long workerId);
}
