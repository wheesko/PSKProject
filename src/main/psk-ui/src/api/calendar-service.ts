import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { LearningDayRequest } from './model/learning-day-request';
import { AxiosResponse } from 'axios';
import { LearningEvent } from '../models/learningEvent';

class CalendarService {
    private readonly restService: RestService;

    constructor(cancelSource: CancelSource = new CancelSource()) {
    	this.restService = cancelSource.service;
    }

    public getMonthLearningDays = (request: LearningDayRequest): Promise<LearningEvent[]> => {
    	return this.restService
    		.get<LearningEvent[]>(
    			`/learningDays/get/${request.selectedYear}/${request.selectedMonth}/${request.workerId}`
    		)
    		.then((response: AxiosResponse<LearningEvent[]>) => {
    			return response.data;
    		});
    }; 
}

const calendarService = new CalendarService();

export { CalendarService };
export { calendarService as default };
