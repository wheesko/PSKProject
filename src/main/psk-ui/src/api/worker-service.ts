import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { RegisterWorkerRequest } from './model/register-worker-request';

class WorkerService {
	private readonly restService: RestService;

	constructor(cancelSource: CancelSource = new CancelSource()) {
		this.restService = cancelSource.service;
	}

	public registerWorker = (registerWorkerRequest: RegisterWorkerRequest): Promise<void> => {
		return this.restService
			.post<void>(
				'/workers/update', registerWorkerRequest
			)
			.then((response: AxiosResponse<void>) => {
				return response.data;
			});
	};
}

const workerService = new WorkerService();

export { WorkerService };
export { workerService as default };
