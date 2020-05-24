import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { LearningTopic } from '../models/learningTopic';

class TopicService {
    private readonly restService: RestService;

    constructor(cancelSource: CancelSource = new CancelSource()) {
    	this.restService = cancelSource.service;
    }

    public getAllTopics = (): Promise<LearningTopic[]> => {
    	return this.restService
    		.get<LearningTopic[]>(
    			'/topics/getAll'
    		)
    		.then((response: AxiosResponse<LearningTopic[]>) => {
    			return response.data;
    		});
    };
}

const topicService = new TopicService();

export { TopicService };
export { topicService as default };
