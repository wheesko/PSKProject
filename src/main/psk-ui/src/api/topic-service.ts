import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { LearningTopic } from '../models/learningTopic';
import { TopicCreateRequest } from './model/topic-create-request';
import { WorkerWithTopics } from "./model/topic-by-manager-response";

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

	public createNewTopic = (topicCreateRequest: TopicCreateRequest): Promise<void> => {
		return this.restService
			.post<void>(
				'/topics/create', topicCreateRequest
			)
			.then((response: AxiosResponse<void>) => {
				return response.data;
			});
	};

	public getWorkersTopicsByManager = (): Promise<WorkerWithTopics[]> =>{
		return this.restService
			.get<WorkerWithTopics[]>(
				'/topics/getWorkersTopics'
			)
			.then((response: AxiosResponse<WorkerWithTopics[]>) => {
				return response.data;
			})
	}
}

const topicService = new TopicService();

export { TopicService };
export { topicService as default };
