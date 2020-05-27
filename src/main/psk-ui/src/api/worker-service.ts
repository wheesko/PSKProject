import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { RegisterWorkerRequest } from './model/register-worker-request';
import { Employee } from '../models/employee';
import { getRoleColor } from '../tools/roleColorPicker';
import { EmployeeResponse } from './model/employee-response';
import { AddEmployeeRequest } from './model/add-employee-request';

class WorkerService {
	private readonly restService: RestService;

	constructor(cancelSource: CancelSource = new CancelSource()) {
		this.restService = cancelSource.service;
	}

	public registerWorker = (registerWorkerRequest: RegisterWorkerRequest): Promise<AxiosResponse | null> => {
		return this.restService.put<void>('/workers/registerWorker', registerWorkerRequest);
	};
	public getEmployees = (): Promise<Employee[]> => {
		return this.restService.get<EmployeeResponse>('/workers/getEmployees',).then((response: AxiosResponse) => {
			return response.data.map((record: EmployeeResponse) => {
				return {
					consecutiveLearningDayLimit: record?.consecutiveLearningDayLimit,
					email: record?.email,
					goals: [],
					id: record?.id,
					managedTeam: record?.managedTeam,
					managerId: record?.managerId,
					name: record?.name,
					quarterLearningDayLimit: record?.quarterLearningDayLimit,
					role: record.role === null ? { name: '', color: '' } : {
						name: record?.role.name,
						color: getRoleColor(record?.role.name)
					},
					surname: record?.surname,
					team: record?.workingTeam.name,
					workingTeam: record?.workingTeam,
				};
			});
		});
	};
	public getColleagues = (): Promise<Employee[]> => {
		return this.restService.get<EmployeeResponse>('/workers/getColleagues',).then((response: AxiosResponse) => {
			return response.data.map((record: EmployeeResponse) => {
				return {
					consecutiveLearningDayLimit: record?.consecutiveLearningDayLimit,
					email: record?.email,
					goals: [],
					id: record?.id,
					managedTeam: record?.managedTeam,
					managerId: record?.managerId,
					name: record?.name,
					quarterLearningDayLimit: record?.quarterLearningDayLimit,
					role: record.role === null ? { name: '', color: '' } : {
						name: record?.role.name,
						color: getRoleColor(record?.role.name)
					},
					surname: record?.surname,
					team: record?.workingTeam.name,
					workingTeam: record?.workingTeam,
				};
			});
		});
	};

	public addEmployee = (addEmployeeRequest: AddEmployeeRequest): Promise<AxiosResponse | null> => {
		return this.restService.post<void>('/workers/create', addEmployeeRequest);
	};

}

const workerService = new WorkerService();

export { WorkerService };
export { workerService as default };
