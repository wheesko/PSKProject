import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { LearningTopic } from '../models/learningTopic';
import { TopicCreateRequest } from './model/topic-create-request';
import { TopicByManagerResponse } from "./model/topic-by-manager-response";

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

	public getWorkersTopicsByManager = (): Promise<TopicByManagerResponse[]> =>{
		return this.restService
			.get<TopicByManagerResponse[]>(
				'/topics/getWorkersTopics'
			)
			.then((response: AxiosResponse<TopicByManagerResponse[]>) => {
				return response.data;
			})
	}
}

const topicService = new TopicService();

export { TopicService };
export { topicService as default };
