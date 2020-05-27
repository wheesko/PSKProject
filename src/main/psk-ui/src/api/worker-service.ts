import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { RegisterWorkerRequest } from './model/register-worker-request';
import jwt from 'jsonwebtoken';

class WorkerService {
	private readonly restService: RestService;

	constructor(cancelSource: CancelSource = new CancelSource()) {
		this.restService = cancelSource.service;
	}

	public registerWorker = (registerWorkerRequest: RegisterWorkerRequest): Promise<AxiosResponse | null> => {
		return this.restService.put<void>('/workers/registerWorker', registerWorkerRequest);
	};
}

const workerService = new WorkerService();

export { WorkerService };
export { workerService as default };
