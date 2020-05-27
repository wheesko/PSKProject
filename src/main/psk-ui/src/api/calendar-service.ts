import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { LearningDayRequest } from './model/learning-day-request';
import { AxiosResponse } from 'axios';
import { LearningEvent } from '../models/learningEvent';
import { TeamLearningDaysResponse } from './model/team-learning-days-response';

class CalendarService {
    private readonly restService: RestService;

    constructor(cancelSource: CancelSource = new CancelSource()) {
    	this.restService = cancelSource.service;
    }

    public getMonthLearningDays = (request: LearningDayRequest): Promise<LearningEvent[]> => {
    	return this.restService
    		.get<LearningEvent[]>(
    			`/learningDays/get/${request.selectedYear}/${request.selectedMonth}`
    		)
    		.then((response: AxiosResponse<LearningEvent[]>) => {
    			return response.data;
    		});
    };

	public getTeamMonthLearningDays = (request: LearningDayRequest): Promise<TeamLearningDaysResponse[]> => {
		return this.restService
			.get<TeamLearningDaysResponse[]>(
				`/learningDays/getByManagerId/${request.selectedYear}/${request.selectedMonth}`
			)
			.then((response: AxiosResponse<TeamLearningDaysResponse[]>) => {
				return response.data;
			});
	};
}

const calendarService = new CalendarService();

export { CalendarService };
export { calendarService as default };
