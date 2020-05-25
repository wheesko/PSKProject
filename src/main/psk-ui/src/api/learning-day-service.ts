import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { LearningDayCreateRequest } from './model/learning-day-create-request';

class LearningDayService {
    private readonly restService: RestService;

    constructor(cancelSource: CancelSource = new CancelSource()) {
    	this.restService = cancelSource.service;
    }

    public createLearningDay = (learningEvent: LearningDayCreateRequest): Promise<void> => {
    	return this.restService
    		.post<void>(
    			'/learningDays/create', learningEvent
    		)
    		.then((response: AxiosResponse<void>) => {
    			return response.data;
    		});
    };
}

const learningDayService = new LearningDayService();

export { LearningDayService };
export { learningDayService as default };
