import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { RegisterWorkerRequest } from './model/register-worker-request';
import { WorkerResponseModel } from './model/worker-response-model';
import { WorkerGoalCreateRequest } from './model/worker-goal-create-request';

class WorkerService {
	private readonly restService: RestService;

	constructor(cancelSource: CancelSource = new CancelSource()) {
		this.restService = cancelSource.service;
	}

	public registerWorker = (registerWorkerRequest: RegisterWorkerRequest): Promise<AxiosResponse | null> => {
		return this.restService.put<void>('/workers/registerWorker', registerWorkerRequest);
	};

	public getWorker = (id: number): Promise<WorkerResponseModel> => {
		return this.restService.get<WorkerResponseModel>(`/workers/get/${id}`)
			.then(response => response.data);
	};

	public assignGoal = (goal: WorkerGoalCreateRequest): Promise<void> => {
		return this.restService.post<void>('/workerGoals/create', goal)
			.then(response => response.data);
	};
}

const workerService = new WorkerService();

export { WorkerService };
export { workerService as default };
